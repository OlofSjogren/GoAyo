package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-16
 * <p>
 * Class representing Account.
 * <p>
 * 2020-09-17 Modified by Alex Phu and Olof Sjögren: Continued implementing methods.
 * Changed boolean functions to throw exceptions instead.
 * 2020-09-18 Modified by Oscar Sanner: Added method to check if loggedInUser is set before
 * running methods requiring the user to be logged in. Added getters needed by high level
 * classes and by extension, the view and controller package.
 * 2020-09-21 Modified by Alex Phu and Oscar Sanner: Implemented Leave and remove feature.
 * 2020-09-23 Modified by Olof Sjögren: getGroupFromID-method is now public and documented, called upon by ModelEngine to provide data to view.
 * 2020-09-28 Modified by Oscar Sanner and Olof Sjögren: Reworked methods to depend on the database for calls before mutating model.
 * 2020-09-28 Modified by Yenan Wang: refactor to add parameter description to createDebt method
 * 2020-09-29 Modified by Oscar Sanner and Olof Sjögren: Fixed bug in login user method. Contact list will now mutate
 * the instance variable contactList instead of returning a new list. Also removed the getUserFromId method as this now
 * resides in the mock database.
 * 2020-09-30 Modified by Oscar Sanner and Olof Sjögren: Added log out method.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Made package private.
 * 2020-10-09 Modified by Alex Phu and Yenan Wang: Added IDebtSplitStrategy to createDebt's parameter.
 * 2020-10-11 Modidied by Oscar Sanner: Made sure the logged in user gets added to the groups he creates.
 * 2020-10-11 Modidied by Oscar Sanner: UUIDs are created in this class and passed to the database and the groups respectively.
 * 2020-10-12 Modified by Alex Phu: In createDebt and payOffDebt, publish for GroupsEvent too on EventBus
 * 2020-10-13 Modified by Olof Sjögren: Refactored eventbus publications to publish enum-types instead of objects.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 * 2020-10-14 Modified by Oscar Sanner: Removed the method get group from ID. This method was obsolete and relying on the database rather than the object oriented model.
 */
class Account {

    private FromJsonFactory fromJsonFactory;
    private User loggedInUser;
    private Set<Group> associatedGroups;
    private Set<User> contactList;
    private IDatabase database;

    /**
     * Constructor for Account class.
     *
     * @param database Instance of database.
     */
    public Account(IDatabase database) {
        this.database = database;
        this.fromJsonFactory = new FromJsonFactory();
    }

    /**
     * A method for registering a user.
     *
     * @param phoneNumber the registered user's phonenumber.
     * @param name        the registered user's name.
     * @param password    the registered user's password.
     * @throws RegistrationException thrown if the registration failed.
     * @throws ConnectException      thrown if unable to make a database connection.
     */
    public void registerUser(String phoneNumber, String password, String name) throws RegistrationException, ConnectException {
        database.registerUser(phoneNumber, password, name);
    }

    /**
     * Logs in the user if logged in information is matched in the database.
     * Also initializes contactList and associatedGroups for the user.
     * Finally publishes a CONTACT_EVENT to the EventBus.
     *
     * @param phoneNumber Phone number input.
     * @param password    Password input.
     * @throws Exception Thrown if user input is not valid. //TODO NEEEEDS TO SPECIFY
     */
    public void loginUser(String phoneNumber, String password) throws Exception {
        JsonString.UserJsonString userToBeLoggedIn = database.getUserToBeLoggedIn(phoneNumber, password);
        loggedInUser = fromJsonFactory.getUser(userToBeLoggedIn);
        initContactList();
        initAssociatedGroups();
        EventBus.getInstance().publish(EventBus.EVENT.CONTACT_EVENT);
    }

    /**
     * Creates a group with the logged in user and all of the users with the given phone numbers.
     * Generates an id for the group and attempts to register the group in the database.
     * Finally publishes a GROUPS_EVENT to the EventBus.
     *
     * @param groupName      Group's name.
     * @param phoneNumberSet Set with all of the potential users' phone numbers.
     * @throws RegistrationException thrown if the registration fails in the database.
     * @throws ConnectException      thrown if unable to connect to the database.
     * @throws UserNotFoundException thrown if a user with the given phone number can't be found in the database. //TODO NEVER THROWN?
     */
    //TODO: Remove all but last two lines?    <---- What does this mean? Still relevant?
    public void createGroup(String groupName, Set<String> phoneNumberSet) throws RegistrationException, ConnectException, UserNotFoundException {
        userIsLoggedIn();
        phoneNumberSet.add(loggedInUser.getPhoneNumber());
        String id = UUID.randomUUID().toString();
        database.registerGroup(groupName, phoneNumberSet, id);
        Set<User> usersToBeAdded = new HashSet<>();

        for (String phoneNumber : phoneNumberSet) {
            if (!phoneNumber.equals(loggedInUser.getPhoneNumber())) {
                usersToBeAdded.add(getUserFromSet(phoneNumber, contactList));
            }
        }
        usersToBeAdded.add(loggedInUser);

        associatedGroups.add(new Group(groupName, id, usersToBeAdded));
        EventBus.getInstance().publish(EventBus.EVENT.GROUPS_EVENT);
    }

    /**
     * Helper method for retrieving a user from a set given a phone number.
     *
     * @param phoneNumber the phone number of the user which is to be found in the set.
     * @param set         the set of user's to search for a user with a matching phone number.
     * @return the user with the matching phone number.
     */
    private User getUserFromSet(String phoneNumber, Set<User> set) {
        for (User u : set) {
            if (u.getPhoneNumber().equals(phoneNumber)) {
                return u;
            }
        }
        //TODO still throw runtime?
        throw new RuntimeException("Something went wrong, selected user can not be found in contactList");
    }

    /**
     * Adds a user to the contactList if a user with the matching phone number exists in the database.
     * Finally publishes a CONTACT_EVENT to the EventBus.
     *
     * @param phoneNumber The to be added user's phone number.
     * @throws UserNotFoundException      thrown if a user with the given phone number can't be found in the database.
     * @throws ConnectException           thrown if unable to connect to the database.
     * @throws UserAlreadyExistsException thrown if a user with the given phone number already exists in the contactList.
     */
    public void addContact(String phoneNumber) throws UserNotFoundException, ConnectException, UserAlreadyExistsException {
        userIsLoggedIn();
        JsonString.UserJsonString jsonString = database.getUser(phoneNumber);

        User u = fromJsonFactory.getUser(jsonString);

        // prevent adding yourself as a contact
        if (u.equals(loggedInUser)) {
            throw new UserAlreadyExistsException("Cannot add yourself as a contact!");
        }

        // iterate through the whole Set,
        // slightly inefficient, however HashSet.contains() does not work,
        // HashSet.contains checks reference for key, it does not call equals()
        // TODO possibly fix this?
        for (User user : contactList) {
            if (user.equals(u)) {
                throw new UserAlreadyExistsException("You have already added this user as a contact!");
            }
        }

        database.addContact(loggedInUser.getPhoneNumber(), phoneNumber);
        contactList.add(u);
        EventBus.getInstance().publish(EventBus.EVENT.CONTACT_EVENT);
    }

    /**
     * Adds a user from the user's contacts to a specific group. Also attempts to update the database accordingly.
     * Finally publishes a GROUPS_EVENT to the EventBus.
     *
     * @param phoneNumber The to be added user's phone number.
     * @param groupID     The id of the group.
     * @throws UserNotFoundException      thrown if a user with the given phone number can't be found in the database.
     * @throws UserAlreadyExistsException thrown if a user with the given phone number already exists in the group.
     * @throws ConnectException           thrown if unable to connect to the database.
     * @throws GroupNotFoundException     thrown if a group with the given groupID can't be found in the database.
     */
    public void addUserToGroup(String phoneNumber, String groupID) throws UserNotFoundException, UserAlreadyExistsException, ConnectException, GroupNotFoundException {
        userIsLoggedIn();
        database.addUserToGroup(groupID, phoneNumber);
        User u = getUserFromSet(phoneNumber, contactList);
        Group g = getAssociatedGroupFromId(groupID);
        g.addUser(u);
        EventBus.getInstance().publish(EventBus.EVENT.SPECIFIC_GROUP_EVENT);
    }


    /**
     * Method for retrieving a group the logged in user is a member of, given the groupID.
     *
     * @param groupID the id of the group to retrieve.
     * @return the group with the matching id.
     */
    public Group getAssociatedGroupFromId(String groupID) {
        for (Group g : associatedGroups) {
            if (g.getGroupID().equals(groupID)) {
                return g;
            }
        }
        //TODO Runtime ok here?
        throw new RuntimeException("Group was not in group list. Something went wrong");
    }

    /**
     * Removes a user from a group the logged in user is a member of.  Also attempts to update the database accordingly.
     * Finally publishes a SPECIFIC_GROUP_EVENT to the EventBus.
     *
     * @param phoneNumber the phone number of the user to be removed.
     * @param groupID     the id of the group from which a user is to be removed.
     * @throws UserNotFoundException  thrown if a user with the given phone number can't be found in the database.
     * @throws GroupNotFoundException thrown if a group with the given groupID can't be found in the database.
     * @throws ConnectException       thrown if unable to connect to the database.
     */
    public void removeUserFromGroup(String phoneNumber, String groupID) throws UserNotFoundException, GroupNotFoundException, ConnectException {
        userIsLoggedIn();
        database.removeUserFromGroup(phoneNumber, groupID);

        Group g = getAssociatedGroupFromId(groupID);
        User u = getUserFromSet(phoneNumber, g.getGroupMembers());
        g.removeUser(u);

        EventBus.getInstance().publish(EventBus.EVENT.SPECIFIC_GROUP_EVENT);
    }

    /**
     * Creates a debt between a lender and one or more borrowers using a split strategy. Also attempts to update the database accordingly.
     * Finally publishes a SPECIFIC_GROUP_EVENT and a GROUPS_EVENT to the EventBus.
     *
     * @param groupID       The group's ID.
     * @param lender        The lender.
     * @param borrowers     The borrower(s).
     * @param owed          Amount of owed money.
     * @param description   the brief description of the debt.
     * @param splitStrategy How the debt is split.
     * @throws Exception //TODO NEEEDS TO SPECIFY (Thrown if group or users are not found, or if the set of borrower is empty.)
     */
    public void createDebt(String groupID, String lender, Set<String> borrowers, BigDecimal owed, String description, IDebtSplitStrategy splitStrategy) throws Exception {
        userIsLoggedIn();

        Map<IUserData, String> borrowerIUserDataAndId = new HashMap<>();
        Map<User, String> borrowerUserAndId = new HashMap<>();

        Group g = getAssociatedGroupFromId(groupID);

        for (String borrower : borrowers) {
            String id = UUID.randomUUID().toString();
            borrowerIUserDataAndId.put(getUserFromSet(borrower, g.getGroupMembers()), id);
            borrowerUserAndId.put(getUserFromSet(borrower, g.getGroupMembers()), id);
        }

        if (borrowerIUserDataAndId.size() == 0) {
            throw new DebtException("The borrowers cannot be empty!");
        }

        if (borrowerIUserDataAndId.get(getUserFromSet(lender, g.getGroupMembers())) != null) {
            throw new DebtException("The lender cannot be a borrower!");
        }

        if (description.isEmpty()) {
            throw new DebtException("The description cannot be empty!");
        }

        database.addDebt(groupID, lender, borrowerIUserDataAndId, owed, description, splitStrategy);

        User lenderUser = getUserFromSet(lender, g.getGroupMembers());

        g.createDebt(lenderUser, borrowerUserAndId, owed, description, splitStrategy);
        EventBus.getInstance().publish(EventBus.EVENT.SPECIFIC_GROUP_EVENT);
        EventBus.getInstance().publish(EventBus.EVENT.GROUPS_EVENT);
    }

    /**
     * Pays off a certain amount of a specific debt. Also attempts to update the database accordingly.
     * Finally publishes a SPECIFIC_GROUP_EVENT and a GROUPS_EVENT to the EventBus.
     *
     * @param amount  Amount to be payed.
     * @param debtID  ID of the debt.
     * @param groupID ID of the group in which the payment is to be made.
     * @throws InvalidDebtException    thrown if the debt is invalid.
     * @throws InvalidPaymentException thrown if the payment is invalid.
     * @throws GroupNotFoundException  thrown if a group with the given id couldn't be found.
     * @throws ConnectException        thrown if unable to connect to the database.
     */
    //Todo: Database.PayOfDebt -> No need to reload groups because same objects. Reload anyways.
    public void payOffDebt(BigDecimal amount, String debtID, String groupID) throws InvalidDebtException, InvalidPaymentException, GroupNotFoundException, ConnectException {
        userIsLoggedIn();
        String id = UUID.randomUUID().toString();
        database.addPayment(groupID, debtID, amount, id);

        Group g = getAssociatedGroupFromId(groupID);
        g.payOffDebt(amount, debtID);

        EventBus.getInstance().publish(EventBus.EVENT.SPECIFIC_GROUP_EVENT);
        EventBus.getInstance().publish(EventBus.EVENT.GROUPS_EVENT);
    }

    /**
     * Getter for the logged in user. Returns the abstract type IUserData.
     * <p>
     * Precondition: The user is logged in via the logInUser method, before calling
     * this method.
     *
     * @return the logged in user wrapped in the IUserData type.
     */
    public IUserData getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * The public getter for the groups associated to the logged in user.
     *
     * @return A set with objects typed IGroupData, providing group information.
     */
    public Set<IGroupData> getAssociatedGroups() {
        return new HashSet<>(associatedGroups);
    }

    /**
     * The public getter for the contact list of the logged in user.
     *
     * @return a new HashSet of the contact list with each user object as a IUserData type.
     */
    public Set<IUserData> getContacts() {
        return new HashSet<>(contactList);
    }

    /**
     * Removes a user from the contactList. Also attempts to update the database accordingly.
     * Finally publishes a CONTACT_EVENT to the EventBus.
     *
     * @param phoneNumber the phone number of the contact to be removed.
     * @throws UserNotFoundException thrown if a user with the given phone number is not a contact of the logged in user.
     * @throws ConnectException      thrown if unable to connect to the database.
     */
    public void removeContact(String phoneNumber) throws UserNotFoundException, ConnectException {
        userIsLoggedIn();
        database.removeContact(loggedInUser.getPhoneNumber(), phoneNumber);
        User u = getUserFromSet(phoneNumber, contactList);
        contactList.remove(u);

        EventBus.getInstance().publish(EventBus.EVENT.CONTACT_EVENT);
    }

    /**
     * Method for the logged in user to leave a specific group. Also attempts to update the database accordingly.
     * Finally publishes a GROUPS_EVENT to the EventBus
     *
     * @param groupID the id of the group the logged in user will leave.
     * @throws UserNotFoundException thrown if the logged in user is not found in the group it wishes to leave.
     * @throws GroupNotFoundException thrown if a group with the given group id can't be found.
     * @throws ConnectException thrown if unable to connect to the database.
     */
    public void leaveGroup(String groupID) throws UserNotFoundException, GroupNotFoundException, ConnectException {
        database.removeUserFromGroup(loggedInUser.getPhoneNumber(), groupID);
        Group g = getAssociatedGroupFromId(groupID);
        g.removeUser(loggedInUser);
        associatedGroups.remove(g);
        EventBus.getInstance().publish(EventBus.EVENT.GROUPS_EVENT);
    }

    /**
     * A method for logging out the current user. This method will make sure that no groups
     * can be saved between logins and that no contacts are saved between logins.
     */
    public void logOutUser() {
        userIsLoggedIn();
        loggedInUser = null;
        associatedGroups = null;
        contactList = null;
    }

    /**
     * Makes a database call to load the contact list of the logged in user, given the logged in users phone number.
     *
     * @throws UserNotFoundException thrown if a user withe given phone number couldn't be found in the database.
     * @throws ConnectException thrown if unable to connect to the database.
     */
    private void initContactList() throws UserNotFoundException, ConnectException {
        JsonString.UserArrayJsonString contactListJson = database.getContactList(getLoggedInUser().getPhoneNumber());
        contactList = fromJsonFactory.getContactList(contactListJson);
    }

    /**
     * Makes a database call and initializes the logged in users groups.
     * @throws Exception TODO NEEEDS TO SPECIFY
     */
    private void initAssociatedGroups() throws Exception {
        JsonString.GroupArrayJsonString associatedGroupsJson = database.getGroups(loggedInUser.getPhoneNumber());
        associatedGroups = fromJsonFactory.getGroups(associatedGroupsJson);
    }

    /**
     * Checks that the user is logged in, otherwise an exception is thrown.
     */
    //TODO NEEDS TO THROW EXCEPTION
    private void userIsLoggedIn() {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException("The user is not logged in");
        }
    }

    /**
     * Retrieves a single user from the database given a phone number.
     * @param phoneNumber the phone number of the user to retrieve.
     * @return the user with the matching phone number.
     * @throws UserNotFoundException thrown if a user with the given phone number couldn't be found.
     * @throws ConnectException thrown if unable to connect to the database.
     */
    public IUserData getSingleUserFromDatabase(String phoneNumber) throws UserNotFoundException, ConnectException {
        JsonString.UserJsonString userJson = database.getUser(phoneNumber);
        return fromJsonFactory.getUser(userJson);
    }

    /**
     * Updates the logged in user's associated groups, groups with membership.
     *
     * @throws Exception //TODO Need to specify.
     */
    public void refreshWithDatabase() throws Exception {
        userIsLoggedIn();
        initAssociatedGroups();
        EventBus.getInstance().publish(EventBus.EVENT.GROUPS_EVENT);
        EventBus.getInstance().publish(EventBus.EVENT.SPECIFIC_GROUP_EVENT);
    }
}
