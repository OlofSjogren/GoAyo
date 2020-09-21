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
 *
 * 2020-09-17 Modified by Alex Phu and Olof Sjögren: Continued implementing methods.
 * Changed boolean functions to throw exceptions instead.
 *
 * 2020-09-18 Modified by Oscar Sanner: Added method to check if loggedInUser is set before
 * running methods requiring the user to be logged in. Added getters needed by high level
 * classes and by extension, the view and controller package.
 *
 * 2020-09-21 Modified by Alex and Oscar: Implemented Leave and remove feature.
 */
public class Account {

    /**
     * Constructor for Account class.
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
     * @param phoneNumber the registered user's phonenumber.
     * @param name        the registered user's name.
     * @param password    the registered user's password.
     * @throws Exception  Thrown if registration failed.
     **/
    public void registerUser(String phoneNumber, String password, String name) throws Exception {
        database.registerUser(phoneNumber, password, name);
    }

    /**
     * Logs in the user if logged in information is matched in the database.
     * Also initializes contactList and associatedGroups for the user.
     * @param phoneNumber Phonenumber input.
     * @param password    Password input.
     * @throws Exception Thrown if user input is not valid.
     */
    public void loginUser(String phoneNumber, String password) throws Exception {
        loggedInUser = database.getUserToBeLoggedIn(phoneNumber, password);
        contactList = initContactList(phoneNumber);
        associatedGroups = loadAssociatedGroups();
    }

    /**
     * Creates a group.
     * @param groupName Group's name.
     * @param phoneNumberSet Set with all of the potential users' phonenumber.
     * @throws Exception Thrown if user is not found in the database.
     */
    public void createGroup(String groupName, Set<String> phoneNumberSet) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Set<User> usersToBeAdded = new HashSet<>();
        for (String s : phoneNumberSet) {
            usersToBeAdded.add(database.getUser(s));
        }
        database.registerGroup(groupName, usersToBeAdded);
        associatedGroups = database.getGroups(loggedInUser.getPhoneNumber());
    }

    /**
     * Adds a user to the contactList.
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
        User tempUser = database.getUser(phoneNumber);
        if(!contactList.contains(tempUser)){
            contactList.add(tempUser);
        }
        else {
            //Todo ("Create a more specific exception")
            throw new Exception();
        }
    }

    /**
     * Adds a user to a specific group.
     * @param phoneNumber The to be added user's phoneNumber.
     * @param groupID The id of the group.
     * @throws Exception Thrown if the group is not found given the groupID, or if the user is not found, or if the user is
     * already in the group.
     */
    public void addUserToGroup(String phoneNumber, String groupID) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Group tempGroup = getGroupFromID(groupID);
        tempGroup.addUser(getUserFromID(phoneNumber));
    }

    /**
     * Removes a user from a group.
     * @param phoneNumber User's phoneNumber.
     * @param groupID Groups ID.
     * @throws Exception Thrown if group is not found given the groupID, or if the user is not found,
     * or if the user is not found given the group.
     */
    public void removeUserFromGroup(String phoneNumber, String groupID) throws Exception {        try {
        userIsLoggedIn();
    } catch (Exception e) {
        e.printStackTrace();
        return;
    }
        Group tempGroup = getGroupFromID(groupID);
        tempGroup.removeUser(getUserFromID(phoneNumber));
    }

    /**
     * Creates a debt between a lender and one or more borrowers.
     * @param groupID The group's ID.
     * @param lender The lender.
     * @param borrower The borrower(s).
     * @param owed Amount of owed money.
     * @throws Exception Thrown if group or users are not found, or if the set of borrower is empty.
     */
    public void createDebt(String groupID, String lender, Set<String> borrower, double owed) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if(borrower.isEmpty()){
            //TODO ("Specify Exception")
            throw new Exception();
        }
        Group tempGroup = getGroupFromID(groupID);
        User lenderUser = getUserFromID(lender);
        Set<User> borrowerUsers = new HashSet<>();
        //TODO ("Create method for creating a set of users given a set of ID's")
        for(String s: borrower){
            borrowerUsers.add(getUserFromID(s));
        }
        //TODO ("Check database if debt is created before delegating task to tempGroup")
        tempGroup.createDebt(lenderUser, borrowerUsers, owed);
    }

    /**
     * Pays off a certain amount of the debt.
     * @param amount Amount to be payed.
     * @param debtID ID of the debt.
     * @param groupID Group's ID.
     * @throws Exception Thrown if group is not found, or if....
     */
    public void payOffDebt(double amount, String debtID, String groupID) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Group tempGroup = getGroupFromID(groupID);
        //TODO ("Check database if payment is registered")
        tempGroup.payOffDebt(amount, debtID);
    }

    /**
     * Getter for the logged in user. Returns the abstract type IUserData.
     *
     * Precondition: The user is logged in via the logInUser method, before calling
     *               this method.
     *
     * @return the logged in user in the shape of "IUserData".
     */
    public IUserData getLoggedInUser(){
        IUserData abstractUser = loggedInUser;
        return abstractUser;
    }

    /**
     * The public getter for the groups associated to the logged in user.
     *
     * @return A set with objects typed IGroupData, providing group information.
     */
    public Set<IGroupData> getAssociatedGroups(){
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
     * @param phoneNumber The to be removed user's phoneNumber.
     * @throws Exception Thrown if the user is not found in the contact list.
     */
    public void removeContact(String phoneNumber) throws Exception {
        try {
            userIsLoggedIn();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        User userToBeRemoved = database.getUser(phoneNumber);
        if(contactList.contains(userToBeRemoved)){
            contactList.remove(userToBeRemoved);
        }
        else {
            //Todo ("Create a more specific exception")
            throw new Exception();
        }
    }

    public void leaveGroup(String groupID) throws Exception{
        database.removeUserFromGroup(loggedInUser.getPhoneNumber(), groupID);
        loadAssociatedGroups();
    }

    private User getUserFromID(String phoneNumber) {
        return database.getUser(phoneNumber);
    }

    private Group getGroupFromID(String groupID) throws Exception {
        Group group = null;
        for(Group g: associatedGroups){
            if(g.getGroupID().equals(groupID)){
                group = g;
            }
        }
        if(group == null){
            throw new Exception();
        }
        return group;
    }

    private Set<User> initContactList(String phoneNumber) throws Exception {
        return database.getContactList(phoneNumber);
    }

    private Set<Group> loadAssociatedGroups() throws Exception {
        return database.getGroups(loggedInUser.getPhoneNumber());
    }

    private void userIsLoggedIn() throws Exception {
        if (loggedInUser == null){
            throw new Exception();
        }
    }


}
