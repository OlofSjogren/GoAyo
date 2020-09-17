package com.goayo.debtify.model;

import com.goayo.debtify.model.database.IDatabase;

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
    public void registerUser(String phoneNumber, String name, String password) throws Exception {
        database.registerUser(phoneNumber, name, password);
    }

    /**
     * Logs in the user if logged in information is matched in the database.
     * @param phoneNumber Phonenumber input.
     * @param password    Password input.
     * @throws Exception Thrown if user input is not valid.
     */
    public void loginUser(String phoneNumber, String password) throws Exception {
        loggedInUser = database.getUserToBeLoggedIn(phoneNumber, password);
    }

    /**
     * Creates a group.
     * @param groupName Group's name.
     * @param phoneNumberSet Set with all of the potential users' phonenumber.
     * @throws Exception Thrown if user is not found in the database.
     */
    public void createGroup(String groupName, Set<String> phoneNumberSet) throws Exception {
        Set<User> usersToBeAdded = new HashSet<>();
        for (String s : phoneNumberSet) {
            usersToBeAdded.add(database.getUser(s));
        }
        database.registerGroup(groupName, usersToBeAdded);
    }

    /**
     * Adds a user to the contactList.
     * @param phoneNumber The to be added user's phoneNumber.
     * @throws Exception Thrown if the user is not found, or if the user is already in the contactList.
     */
    public void addContact(String phoneNumber) throws Exception {
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
    public void removeUserFromGroup(String phoneNumber, String groupID) throws Exception {
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
        if(borrower.isEmpty()){
            //TODO ("Specify Exception")
            throw new Exception();
        }
        Group tempGroup = getGroupFromID(groupID);
        User lenderUser = getUserFromID(lender);
        Set<User> borrowerUsers = new HashSet<>();

        for(String s: borrower){
            borrowerUsers.add(getUserFromID(s));
        }
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
        Group tempGroup = getGroupFromID(groupID);
        tempGroup.payOffDebt(amount, debtID);
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
}
