package com.goayo.debtify.model;

import java.util.List;

/**
 * A facade class for the model package. The purpose of the class is to be the face outwards towards other
 * packages depending on the model. This class aims to keep the model loosely coupled with other packages.
 *
 * @Author Oscar Sanner
 *
 */

public class ModelEngine {

    private Account account;
    private static ModelEngine instance;

    private ModelEngine(Account account){
        this.account = account;
    }

    /**
     * A standard singleton getInstance function.
     *
     * @return The single instance of the ModelEngine
     */
    public static ModelEngine getInstance(){
        if(instance == null){
            instance = new ModelEngine(new Account());
        }
        return instance;
    }

    /**
     * Register a new user in the database
     *
     * Precondition: The no user with the same phone number exists in the database.
     *
     * @param phoneNumber The phone number of the new user.
     * @param name The name of the new user.
     * @param password The password of the new user.
     *
     * @return true if the operation was successful, server side. False if the precondition
     * is not met, or if some form of connection error occurs.
     */
    public boolean registerUser(String phoneNumber, String name, String password){
        return false;
    }

    /**
     * Logs the user into the application. Other functions related to account management will
     * throw an exception if no user is logged in.
     *
     * Precondition: The user exists in the database.
     * Precondition: The password matches the user in the database.
     *
     * @param phoneNumber Phone number used to find the User in the database.
     * @param password Password associated with the phone number.
     *
     * @return True, if the operation was successful. False if the preconditions are not met, or
     * if some form of connection error occurs.
     */
    public boolean logInUser(String phoneNumber, String password){
        return false;
    }

    /**
     * Adds a contact to the users contact book.
     *
     * @param phoneNumber the phone number of the contact to be added.
     * @return True if the operation was successful, server side.
     */
    public boolean addContact(String phoneNumber){
        return false;
    }

    /**
     * Removes a contact from the users contact book.
     *
     * Precondition: The specified phone number exists in the users phone book.
     *
     * @param phoneNumber The phone number associated to the user that will be removed from
     *                    the contact book.
     *
     * @return True if the operation was successful, server side. False if the precondition
     * is not met, or if some form of connection error occurs.
     */
    public boolean removeContact(String phoneNumber){
        return false;
    }

    /**
     * Creates a new group, associated to the logged in user. The logged in user is
     * automatically added into the new group, along with the user associated to the list
     * of phone numbers.
     *
     * @param groupName The name of the group.
     * @param phoneNumberList A list of phone numbers associated with users who will be added
     *                        into the group.
     *
     * @return True if the operation was successful, server side.
     */
    public boolean createGroup(String groupName, List<String> phoneNumberList){
        return false;
    }

    /**
     * De-associates the user with a group.
     *
     * Precondition: The user is in the group with the specified ID.
     *
     * @param groupID The ID specific to the group that the user will leave.
     *
     * @return True if the operation was successful, server side.  False if the precondition
     * is not met, or if some form of connection error occurs.
     */
    public boolean removeGroup(int groupID){
        return false;
    }

    /**
     * A public getter for all the groups associated with the user.
     *
     * @return an Immutable copy of the type "IGroupInformation".
     */
    public List<IGroupInformation> getGroups(){
        return
    }

    /**
     * 
     * @param phoneNumber
     * @param groupID
     * @return
     */
    public boolean addUserToGroup(String phoneNumber, int groupID){
        return false;
    }
    public boolean removeUserFromGroup(String phoneNumber, int groupID){
        return false;
    }

    public boolean createDebt(int groupID, String lender, List<String> borrower, double owed){
        return false;
    }
    public boolean payOffDebt(double amount, String debtID){
        return false;
    }

    private boolean isThisInDatabase(String id){
        return false;
    }
}
