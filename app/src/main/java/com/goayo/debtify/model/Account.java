package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

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
 * <p>
 * 2020-09-18 Modified by Oscar Sanner: Added method to check if loggedInUser is set before
 * running methods requiring the user to be logged in. Added getters needed by high level
 * classes and by extension, the view and controller package.
 * <p>
 * 2020-09-21 Modified by Alex and Oscar: Implemented Leave and remove feature.
 * <p>
 * 2020-09-23 Modified by Olof: getGroupFromID-method is now public and documented, called upon by ModelEngine to provide data to view.
 * 2020-09-28 Modified by Oscar Sanner and Olof Sjögren: Reworked methods to depend on the database for calls before mutating model.
 * 2020-09-28 Modified by Yenan: refactor to add parameter description to createDebt method
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
 */
class Account {

    /**
     * Constructor for Account class.
     *
     * @param database Instance of database.
     */
    public Account(IDatabase database) {
        this.database = database;
        this.fromJsonFactory = new FromJsonFactory();
    }

    private FromJsonFactory fromJsonFactory;
    private User loggedInUser;
    private Set<Group> associatedGroups;
    private Set<User> contactList;
    private IDatabase database;

    /**
     * A method for registering and
     *
     * @param phoneNumber the registered user's phonenumber.
     * @param name        the registered user's name.
     * @param password    the registered user's password.
     */
    public void registerUser(String phoneNumber, String password, String name) throws RegistrationException, ConnectException {
        database.registerUser(phoneNumber, password, name);
    }

    /**
     * Logs in the user if logged in information is matched in the database.
     * Also initializes contactList and associatedGroups for the user.
     *
     * @param phoneNumber Phonenumber input.
     * @param password    Password input.
     * @throws Exception Thrown if user input is not valid.
     */
    public void loginUser(String phoneNumber, String password) throws Exception {
        String userToBeLoggedIn = database.getUserToBeLoggedIn(phoneNumber, password);
        loggedInUser = fromJsonFactory.getUser(userToBeLoggedIn);
        initContactList(phoneNumber);
        initAssociatedGroups();
        EventBus.getInstance().publish(new ContactEvent());
    }

    /**
     * Creates a group.
     *
     * @param groupName      Group's name.
     * @param phoneNumberSet Set with all of the potential users' phonenumber.
     * @throws Exception Thrown if user is not found in the database.
     */

    //TODO: Remove all but last two lines?
    public void createGroup(String groupName, Set<String> phoneNumberSet) throws RegistrationException, ConnectException, UserNotFoundException {
        userIsLoggedIn();
        phoneNumberSet.add(loggedInUser.getPhoneNumber());
        String id = UUID.randomUUID().toString();
        database.registerGroup(groupName, phoneNumberSet, id);
        Set<User> usersToBeAdded = new HashSet<>();

        for(String phoneNumber : phoneNumberSet){
            if(!phoneNumber.equals(loggedInUser.getPhoneNumber())){
                usersToBeAdded.add(getUserFromSet(phoneNumber, contactList));
            }
        }
        usersToBeAdded.add(loggedInUser);

        associatedGroups.add(new Group(groupName, id, usersToBeAdded));
        EventBus.getInstance().publish(new GroupsEvent());
    }

    private User getUserFromSet(String phoneNumber, Set<User> set) {
        for (User u : set){
            if(u.getPhoneNumber().equals(phoneNumber)){
                return u;
            }
        }
        throw new RuntimeException("Something went wrong, selected user can not be found in contactList");
    }

    /**
     * Adds a user to the contactList.
     *
     * @param phoneNumber The to be added user's phoneNumber.
     * @throws Exception Thrown if the user is not found, or if the user is already in the contactList.
     */

    public void addContact(String phoneNumber) throws UserNotFoundException, ConnectException, UserAlreadyExistsException {
        userIsLoggedIn();
        String data = database.getUser(phoneNumber);

        // TODO Change this to exception
        if (data.equals("BAD REQUEST, INCORRECT NUMBER")) {
            throw new UserNotFoundException("This user doesn't exist!");
        }
        User u = fromJsonFactory.getUser(data);

        // prevent adding yourself as a contact
        if (u.equals(loggedInUser)) {
            throw new UserAlreadyExistsException("Cannot add yourself as a contact!");
        }

        database.addContact(loggedInUser.getPhoneNumber(), phoneNumber);
        contactList.add(u);
        EventBus.getInstance().publish(new ContactEvent());
    }

    /**
     * Adds a user to a specific group.
     *
     * @param phoneNumber The to be added user's phoneNumber.
     * @param groupID     The id of the group.
     * @throws Exception Thrown if the group is not found given the groupID, or if the user is not found, or if the user is
     *                   already in the group.
     */

    public void addUserToGroup(String phoneNumber, String groupID) throws UserNotFoundException, UserAlreadyExistsException, ConnectException, GroupNotFoundException {
        userIsLoggedIn();
        database.addUserToGroup(groupID, phoneNumber);
        User u = getUserFromSet(phoneNumber, contactList);
        Group g = getAssociatedGroupFromId(groupID);
        g.addUser(u);
        EventBus.getInstance().publish(new DetailedGroupEvent());
    }

    public Group getAssociatedGroupFromId(String groupID) {
        for(Group g : associatedGroups){
            if(g.getGroupID().equals(groupID)){
                return g;
            }
        }
        throw new RuntimeException("Group was not in group list. Something went wrong");
    }

    /**
     * Removes a user from a group.
     *
     * @param phoneNumber User's phoneNumber.
     * @param groupID     Groups ID.
     * @throws Exception Thrown if group is not found given the groupID, or if the user is not found,
     *                   or if the user is not found given the group.
     */

    public void removeUserFromGroup(String phoneNumber, String groupID) throws UserNotFoundException, GroupNotFoundException, ConnectException {
        userIsLoggedIn();
        database.removeUserFromGroup(phoneNumber, groupID);

        Group g = getAssociatedGroupFromId(groupID);
        User u = getUserFromSet(phoneNumber, g.getGroupMembers());
        g.removeUser(u);

        EventBus.getInstance().publish(new DetailedGroupEvent());
    }

    /**
     * Creates a debt between a lender and one or more borrowers.
     *
     * @param groupID     The group's ID.
     * @param lender      The lender.
     * @param borrowers   The borrower(s).
     * @param owed        Amount of owed money.
     * @param description the brief description of the debt
     * @param splitStrategy How the debt is split
     * @throws Exception Thrown if group or users are not found, or if the set of borrower is empty.
     */

    public void createDebt(String groupID, String lender, Set<String> borrowers, BigDecimal owed, String description, IDebtSplitStrategy splitStrategy) throws Exception {
        userIsLoggedIn();

        Map<IUserData, String> borrowerIUserDataAndId = new HashMap<>();
        Map<User, String> borrowerUserAndId = new HashMap<>();

        Group g = getAssociatedGroupFromId(groupID);

        for (String borrower : borrowers){
            String id = UUID.randomUUID().toString();
            borrowerIUserDataAndId.put(getUserFromSet(borrower, g.getGroupMembers()), id);
            borrowerUserAndId.put(getUserFromSet(borrower, g.getGroupMembers()), id);
        }




        database.addDebt(groupID, lender, borrowerIUserDataAndId, owed, description, splitStrategy);

        User lenderUser = getUserFromSet(lender, g.getGroupMembers());

        g.createDebt(lenderUser, borrowerUserAndId, owed, description, splitStrategy);
        EventBus.getInstance().publish(new DetailedGroupEvent());
        EventBus.getInstance().publish(new GroupsEvent());
    }

    /**
     * Pays off a certain amount of the debt.
     *
     * @param amount  Amount to be payed.
     * @param debtID  ID of the debt.
     * @param groupID Group's ID.
     * @throws Exception Thrown if group is not found, or if....
     */

    //Todo: Database.PayOfDebt -> No need to reload groups because same objects. Reload anyways.
    public void payOffDebt(BigDecimal amount, String debtID, String groupID) throws InvalidDebtException, InvalidPaymentException, GroupNotFoundException, ConnectException, UserNotFoundException {
        userIsLoggedIn();
        String id = UUID.randomUUID().toString();
        database.addPayment(groupID, debtID, amount, id);

        Group g = getAssociatedGroupFromId(groupID);
        g.payOffDebt(amount, debtID);

        EventBus.getInstance().publish(new DetailedGroupEvent());
        EventBus.getInstance().publish(new GroupsEvent());
    }

    /**
     * Getter for the logged in user. Returns the abstract type IUserData.
     * <p>
     * Precondition: The user is logged in via the logInUser method, before calling
     * this method.
     *
     * @return the logged in user in the shape of "IUserData".
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
     */
    public Set<IUserData> getContacts() {
        return new HashSet<>(contactList);
    }

    /**
     * Removes a user from the contactList.
     *
     * @param phoneNumber The to be removed user's phoneNumber.
     * @throws Exception Thrown if the user is not found in the contact list.
     */

    //Todo: database.removeContact. -> Reload contact list.
    public void removeContact(String phoneNumber) throws UserNotFoundException, ConnectException {
        userIsLoggedIn();
        database.removeContact(loggedInUser.getPhoneNumber(), phoneNumber);
        User u = getUserFromSet(phoneNumber, contactList);
        contactList.remove(u);

        EventBus.getInstance().publish(new ContactEvent());
    }


    //TODO: NEEDS JDOCZ
    public void leaveGroup(String groupID) throws UserNotFoundException, GroupNotFoundException, ConnectException {
        database.removeUserFromGroup(loggedInUser.getPhoneNumber(), groupID);
        Group g = getAssociatedGroupFromId(groupID);
        g.removeUser(loggedInUser);
        associatedGroups.remove(g);
        EventBus.getInstance().publish(new GroupsEvent());
    }

    /**
     * Fetches the group with the specific groupID, provided that the user is a member of the group.
     *
     * @param groupID id of the group to fetch.
     * @return the group with the given id.
     * @throws Exception thrown if the logged in user isn't apart of the group, it can't be found.
     */

    //Todo: Database call?
    public Group getGroupFromID(String groupID) throws Exception {
        String groupJson = database.getGroupFromId(groupID);
        return fromJsonFactory.getGroupFromId(groupJson);
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

    private void initContactList(String phoneNumber) throws UserNotFoundException, ConnectException {
        String contactListJson = database.getContactList(phoneNumber);
        contactList = fromJsonFactory.getContactList(contactListJson);
    }

    private void initAssociatedGroups() throws Exception {
        String associatedGroupsJson = database.getGroups(loggedInUser.getPhoneNumber());
        associatedGroups = fromJsonFactory.getGroups(associatedGroupsJson);
    }

    private void userIsLoggedIn() {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException("The user is not logged in");
        }
    }

    public void refreshWithDatabase() throws Exception {
        userIsLoggedIn();
        initAssociatedGroups();
        EventBus.getInstance().publish(new GroupsEvent());
        EventBus.getInstance().publish(new DetailedGroupEvent());
    }
}
