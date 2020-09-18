package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.List;
import java.util.Set;

/**
 * @Author Oscar Sanner
 * @date 2020-09-15
 *
 * <p>
 * A facade class for the model package. The purpose of the class is to be the face outwards towards other
 * packages depending on the model. This class aims to keep the model loosely coupled with other packages.
 * 
 */

public class ModelEngine {

    private Account account;
    private static ModelEngine instance;
    private IDatabase database;

    private ModelEngine(Account account){
        this.account = account;
        this.database = new MockDatabase();
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
        return account.registerUser(phoneNumber, name, password);
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
        return account.logInUser(phoneNumber, password);
    }


    /**
     * Adds a contact to the users contact book.
     *
     * @param phoneNumber the phone number of the contact to be added.
     * @return True if the operation was successful, server side.
     */
    public boolean addContact(String phoneNumber){
        return account.addContact(phoneNumber);
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
        return account.removeContact(phoneNumber);
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
        return account.createGroup(groupName, phoneNumberList);
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
        return account.removeGroup(groupID);
    }

    /**
     * A public getter for all the groups associated with the user.
     *
     * @return an Immutable copy of the type "IGroupInformation".
     */
    public Set<IGroupData> getGroups(){
        return account.getAssociatedGroups();
    }

    /**
     * Method for adding a user to a group, online and in the program.
     *
     * Precondition: The phone number is registered to a user in the database.
     * Precondition: A group with the given group id exists.
     * Precondition: The user is not already in the group with the given ID.
     *
     * @param phoneNumber The phone number of the user to be added to the group.
     * @param groupID The ID of the group into which the user will be added.
     *
     * @return True if the operation was successful, server side and in the program.
     * False if the preconditions aren't met, or if some form of connection error occurs.
     */
    public boolean addUserToGroup(String phoneNumber, int groupID){
        return account.addUserToGroup(phoneNumber, groupID);
    }

    /**
     * Removes a user from a group.
     *
     * Precondition: The user is in the group associated to the group ID that's sent in
     *               via parameter.
     *
     * @param phoneNumber The phone number associated to the user who is to be removed from
     *                    the group
     * @param groupID The group ID of the group that the user will be removed from.
     *
     * @return True if the operation was successful, server side and in the program.
     * False if the precondition isn't met, or if some form of connection error occurs.
     */
    public boolean removeUserFromGroup(String phoneNumber, int groupID){
        return account.removeUserFromGroup(phoneNumber, groupID);
    }

    /**
     * Creates a new debt on the server and in the application between one lender and one
     * or more borrowers. A debt associated to a group. The sum specified by the owed
     * parameter is devised equally across the list of borrowers, leading to one actual debt per
     * borrower.
     *
     * Precondition: lender and borrower(s) exists in the database.
     * Precondition: The group ID exists in the database.
     * Precondition: Owed has a positive value.
     *
     * @param groupID The ID of the group to which the debt is associated.
     * @param lender The phone number of the user lending the money.
     * @param borrower A list of one or more users.
     * @param owed A positive double, representing the whole value spent by the lender.
     *
     * @return True if the operation was successful, server side and in the program.
     * False if the preconditions aren't met, or if some form of connection error occurs.
     */
    public boolean createDebt(int groupID, String lender, List<String> borrower, double owed){
        return account.createDebt(groupID, lender, borrower, owed);
    }

    /**
     * Crates one payment towards a debt owed by the logged in user.
     *
     * Precondition: The debt with the specified debt ID exists in the application.
     * Precondition: The payment is smaller than or equals to the whole value of the debt.
     * Precondition: The amount is a positive double.
     *
     * @param amount A positive double.
     * @param debtID The ID of the debt towards which the payment will be done.
     *
     * @return True if the operation was successful, server side and in the program.
     * False if the preconditions aren't met, or if some form of connection error occurs.
     */
    public boolean payOffDebt(double amount, String debtID){
        return account.payOffDebt(amount,debtID);
    }

    /**
     * Getter for the logged in user. Returns a specific set of data in form of a
     * IUserData typed object.
     *
     * Precondition: The user is logged in.
     *
     * @return the logged in user, typed as an IUserData object.
     */
    public IUserData getLoggedInUser(){
        return account.getLoggedInUser();
    }

}
