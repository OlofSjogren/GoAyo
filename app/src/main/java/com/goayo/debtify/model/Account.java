package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.HashSet;
import java.util.Set;

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
 */
public class Account {

    /**
     * Constructor for Account class.
     *
     * @param database Instance of database.
     */
    public Account(IDatabase database) {
        this.database = database;
    }

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
    public void registerUser(String phoneNumber, String password, String name) {
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
        loggedInUser = database.getUserToBeLoggedIn(phoneNumber, password);
        if (loggedInUser == null) {
            throw new LoginException("Password or number wrong");
        }
        initContactList(phoneNumber);
        loadAssociatedGroups();
    }

    /**
     * Creates a group.
     *
     * @param groupName      Group's name.
     * @param phoneNumberSet Set with all of the potential users' phonenumber.
     * @throws Exception Thrown if user is not found in the database.
     */

    //TODO: Remove all but last two lines?
    public void createGroup(String groupName, Set<String> phoneNumberSet) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        phoneNumberSet.add(loggedInUser.getPhoneNumber());
        database.registerGroup(groupName, phoneNumberSet);
        associatedGroups = database.getGroups(loggedInUser.getPhoneNumber());
    }

    /**
     * Adds a user to the contactList.
     *
     * @param phoneNumber The to be added user's phoneNumber.
     * @throws Exception Thrown if the user is not found, or if the user is already in the contactList.
     */

    public void addContact(String phoneNumber) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        database.addContact(loggedInUser.getPhoneNumber(), phoneNumber);
        initContactList(loggedInUser.getPhoneNumber());
    }

    /**
     * Adds a user to a specific group.
     *
     * @param phoneNumber The to be added user's phoneNumber.
     * @param groupID     The id of the group.
     * @throws Exception Thrown if the group is not found given the groupID, or if the user is not found, or if the user is
     *                   already in the group.
     */

    public void addUserToGroup(String phoneNumber, String groupID) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        database.addUserToGroup(groupID, phoneNumber);
        loadAssociatedGroups();
    }

    /**
     * Removes a user from a group.
     *
     * @param phoneNumber User's phoneNumber.
     * @param groupID     Groups ID.
     * @throws Exception Thrown if group is not found given the groupID, or if the user is not found,
     *                   or if the user is not found given the group.
     */

    public void removeUserFromGroup(String phoneNumber, String groupID) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        database.removeUserFromGroup(phoneNumber, groupID);
        loadAssociatedGroups();
    }

    /**
     * Creates a debt between a lender and one or more borrowers.
     *
     * @param groupID The group's ID.
     * @param lender The lender.
     * @param borrowers The borrower(s).
     * @param owed Amount of owed money.
     * @param description the brief description of the debt
     * @throws Exception Thrown if group or users are not found, or if the set of borrower is empty.
     */

    public void createDebt(String groupID, String lender, Set<String> borrowers, double owed, String description) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (borrowers.isEmpty()) {
            //TODO ("Specify Exception")
            throw new Exception();
        }
        database.addDebt(groupID, lender, borrowers, owed, description);
        loadAssociatedGroups();
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
    public void payOffDebt(double amount, String debtID, String groupID) throws Exception {

        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        database.addPayment(groupID, debtID, amount);
        loadAssociatedGroups();
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
        Set<IGroupData> retGroup = new HashSet<>();
        retGroup.addAll(associatedGroups);
        return retGroup;
    }

    /**
     * The public getter for the contact list of the logged in user.
     */
    public Set<IUserData> getContacts() {
        Set<IUserData> returnList = new HashSet<>();
        returnList.addAll(contactList);
        return returnList;
    }

    /**
     * Removes a user from the contactList.
     *
     * @param phoneNumber The to be removed user's phoneNumber.
     * @throws Exception Thrown if the user is not found in the contact list.
     */

    //Todo: database.removeContact. -> Reload contact list.
    public void removeContact(String phoneNumber) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        database.removeContact(loggedInUser.getPhoneNumber(), phoneNumber);
        initContactList(loggedInUser.getPhoneNumber());
    }


    //TODO: NEEDS JDOCZ
    public void leaveGroup(String groupID) throws Exception {
        database.removeUserFromGroup(loggedInUser.getPhoneNumber(), groupID);
        loadAssociatedGroups();
    }

    /**
     * Fetches the group with the specific groupID, provided that the user is a member of the group.
     *
     * @param groupID id of the group to fetch.
     * @return the group with the given id.
     * @throws Exception thrown if the logged in user isn't apart of the group, it can't be found.
     */

    //Todo: Database call?
    public Group getGroupFromID(String groupID) {
        return database.getGroupFromId(groupID);
    }


    private void initContactList(String phoneNumber) throws Exception {
        contactList = database.getContactList(phoneNumber);
    }

    private void loadAssociatedGroups() {
        associatedGroups = database.getGroups(loggedInUser.getPhoneNumber());
    }

    private void userIsLoggedIn() throws Exception {
        if (loggedInUser == null) {
            throw new Exception();
        }
    }
}
