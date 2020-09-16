package com.goayo.debtify.model;

import com.goayo.debtify.model.database.IDatabase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Account {

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
     * @returns false if registration was unsuccessful, otherwise true.
     **/
    public boolean registerUser(String phoneNumber, String name, String password) {
        return database.registerUser(phoneNumber, name, password);
    }

    /**
     * Logs in the user if logged in information is matched in the database.
     *
     * @param phoneNumber Phonenumber input.
     * @param password    Password input.
     * @return Returns true if login was successful.
     */
    public boolean loginUser(String phoneNumber, String password) {
        User toBeLoggedIn = database.getUserToBeLoggedIn(phoneNumber, password);
        if (toBeLoggedIn != null) {
            loggedInUser = toBeLoggedIn;
            return true;
        }
        return false;
    }

    /**
     *
     * @param groupName Group's name.
     * @param phoneNumberSet Set with all of the potential users' phonenumber.
     * @return Returns true if ALL of the users were added successfully.
     */
    public boolean createGroup(String groupName, Set<String> phoneNumberSet) {
        Set<User> usersToBeAdded = new HashSet<>();
        for (String s : phoneNumberSet) {
            User potentialUser = database.getUser(s);
            if (potentialUser == null) {
                return false;
            }
            usersToBeAdded.add(potentialUser);
        }
        database.registerGroup(groupName, usersToBeAdded);
        return true;
    }

    public boolean addContact(String phoneNumber) {
        return true;
    }

    public boolean addUserToGroup(String phoneNumber, String groupID) {
        return true;
    }

    public boolean removeUserFromGroup(String phoneNumber, String groupID) {
        return true;
    }

    public boolean payOffDebt(double amount, String lender, Set<String> borrwer, double owed) {
        return true;
    }

    private boolean checkUserInDatabase(String phoneNumber) {
        return true;
    }

    private User retrieveUserInformation(String phoneNumer) {
        return new User(null, null);
    }

    private boolean checkForGroupDatabase(String id) {
        return true;
    }


}
