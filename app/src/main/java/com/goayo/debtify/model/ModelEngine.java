package com.goayo.debtify.model;

import com.goayo.debtify.Database.RealDatabase;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Set;

/**
 * @Author Oscar Sanner
 * @date 2020-09-15
 *
 * <p>
 * A facade class for the model package. The purpose of the class is to be the face outwards towards other
 * packages depending on the model. This class aims to keep the model loosely coupled with other packages.
 * <p>
 * 2020-09-18 Modfied by Olof, Yenan & Alex : removed booleans returns and replaced with exceptions.
 * 2020-09-21 Modified by Alex and Oscar: Implemented Leave and remove feature.
 * 2020-09-23 Modified by Olof: Added getGroup-method provided a specific id.
 * 2020-09-28 Modified by Yenan: refactor to add parameter description to createDebt method
 * 2020-09-28 Modified by Alex: Refactored hardcoded debt data.
 * 2020-09-30 Modified by Oscar Sanner and Olof Sjögren: Added log out method.
 * 2020-09-30 Modified by Olof Sjögren and Oscar Sanner : Now implements IObservable and (for now) notifies on registration and login.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-09 Modified by Alex Phu and Yenan Wang: Added IDebtSplitStrategy to createDebt's parameter.
 * 2020-10-11 Modified by Alex Phu: Fixed wrong order of arguments in registerUser();
 */

public class ModelEngine {

    private Account account;
    private static ModelEngine instance;

    private ModelEngine(Account account) {
        this.account = account;
        //TODO: AUTOMATICALLY LOGS THE USER IN WHEN THIS CLASS IS INSTANTIATED, BECAUSE
        //TODO LOGIN FUNCTIONALITY IS YET TO BE IMPLEMENTED
    }

    /**
     * A standard singleton getInstance function.
     *
     * @return The single instance of the ModelEngine
     */
    public static ModelEngine getInstance() {
        if (instance == null) {
            instance = new ModelEngine(new Account(new RealDatabase()));
        }
        return instance;
    }

    /**
     * Register a new user in the database
     * <p>
     * Precondition: The no user with the same phone number exists in the database.
     *
     * @param phoneNumber The phone number of the new user.
     * @param name        The name of the new user.
     * @param password    The password of the new user.
     * @return true if the operation was successful, server side. False if the precondition
     * is not met, or if some form of connection error occurs.
     */
    public void registerUser(String phoneNumber, String name, String password) throws RegistrationException, ConnectException {
        account.registerUser(phoneNumber, password, name);
    }

    /**
     * Logs the user into the application. Other functions related to account management will
     * throw an exception if no user is logged in.
     * <p>
     * Precondition: The user exists in the database.
     * Precondition: The password matches the user in the database.
     *
     * @param phoneNumber Phone number used to find the User in the database.
     * @param password    Password associated with the phone number.
     * @return True, if the operation was successful. False if the preconditions are not met, or
     * if some form of connection error occurs.
     */
    public void logInUser(String phoneNumber, String password) throws Exception {
        account.loginUser(phoneNumber, password);
    }

    /**
     * Logs the current user out from the model and removes any personal contacts or groups
     * that are stored in the model.
     *
     * Precondition: The user is logged in to the model.
     *
     */
    public void logOutUser(){
        account.logOutUser();
    }


    /**
     * Adds a contact to the users contact book.
     *
     * @param phoneNumber the phone number of the contact to be added.
     * @return True if the operation was successful, server side.
     */
    public void addContact(String phoneNumber) throws Exception {
        account.addContact(phoneNumber);
    }

    /**
     * Removes a contact from the users contact book.
     * <p>
     * Precondition: The specified phone number exists in the users phone book.
     *
     * @param phoneNumber The phone number associated to the user that will be removed from
     *                    the contact book.
     * @return True if the operation was successful, server side. False if the precondition
     * is not met, or if some form of connection error occurs.
     */
    public void removeContact(String phoneNumber) throws Exception {
        account.removeContact(phoneNumber);
    }

    public IUserData getSingleUserFromDatabase(String phoneNumber) throws UserNotFoundException, ConnectException {
        return account.getSingleUserFromDatabase(phoneNumber);
    }

    /**
     * Creates a new group, associated to the logged in user. The logged in user is
     * automatically added into the new group, along with the user associated to the list
     * of phone numbers.
     *
     * @param groupName       The name of the group.
     * @param phoneNumberList A list of phone numbers associated with users who will be added
     *                        into the group.
     * @return True if the operation was successful, server side.
     */
    public void createGroup(String groupName, Set<String> phoneNumberList) throws Exception {
        account.createGroup(groupName, phoneNumberList);
    }

    /**
     * De-associates the user with a group.
     * <p>
     * Precondition: The user is in the group with the specified ID.
     *
     * @param groupID The ID specific to the group that the user will leave.
     * @return True if the operation was successful, server side.  False if the precondition
     * is not met, or if some form of connection error occurs.
     */
    public void leaveGroup(String groupID) throws Exception {
        account.leaveGroup(groupID);
    }

    /**
     * A public getter for all the groups associated with the user.
     *
     * @return an Immutable copy of the type "IGroupInformation".
     */
    public Set<IGroupData> getGroups() {
        return account.getAssociatedGroups();
    }

    /**
     * Method for adding a user to a group, online and in the program.
     * <p>
     * Precondition: The phone number is registered to a user in the database.
     * Precondition: A group with the given group id exists.
     * Precondition: The user is not already in the group with the given ID.
     *
     * @param phoneNumber The phone number of the user to be added to the group.
     * @param groupID     The ID of the group into which the user will be added.
     * @return True if the operation was successful, server side and in the program.
     * False if the preconditions aren't met, or if some form of connection error occurs.
     */
    public void addUserToGroup(String phoneNumber, String groupID) throws Exception {
        account.addUserToGroup(phoneNumber, groupID);
    }

    /**
     * Removes a user from a group.
     * <p>
     * Precondition: The user is in the group associated to the group ID that's sent in
     * via parameter.
     *
     * @param phoneNumber The phone number associated to the user who is to be removed from
     *                    the group
     * @param groupID     The group ID of the group that the user will be removed from.
     * @return True if the operation was successful, server side and in the program.
     * False if the precondition isn't met, or if some form of connection error occurs.
     */
    public void removeUserFromGroup(String phoneNumber, String groupID) throws Exception {
        account.removeUserFromGroup(phoneNumber, groupID);
    }

    /**
     * Creates a new debt on the server and in the application between one lender and one
     * or more borrowers. A debt associated to a group. The sum specified by the owed
     * parameter is devised equally across the list of borrowers, leading to one actual debt per
     * borrower.
     * <p>
     * Precondition: lender and borrower(s) exists in the database.
     * Precondition: The group ID exists in the database.
     * Precondition: Owed has a positive value.
     *
     * @param groupID  The ID of the group to which the debt is associated.
     * @param lender   The phone number of the user lending the money.
     * @param borrower A list of one or more users.
     * @param owed     A positive double, representing the whole value spent by the lender.
     * @param description A short string, preferably <20 characters, that describes the debt
     * @param splitStrategy How the debt is split
     * @return True if the operation was successful, server side and in the program.
     * False if the preconditions aren't met, or if some form of connection error occurs.
     */
    public void createDebt(String groupID, String lender, Set<String> borrower, BigDecimal owed, String description, IDebtSplitStrategy splitStrategy) throws Exception {
        account.createDebt(groupID, lender, borrower, owed, description, splitStrategy);
    }

    /**
     * Crates one payment towards a debt owed by the logged in user.
     * <p>
     * Precondition: The debt with the specified debt ID exists in the application.
     * Precondition: The payment is smaller than or equals to the whole value of the debt.
     * Precondition: The amount is a positive double.
     *
     * @param amount A positive double.
     * @param debtID The ID of the debt towards which the payment will be done.
     * @return True if the operation was successful, server side and in the program.
     * False if the preconditions aren't met, or if some form of connection error occurs.
     */
    public void payOffDebt(BigDecimal amount, String debtID, String groupID) throws Exception {
        account.payOffDebt(amount, debtID, groupID);
    }

    /**
     * Getter for the logged in user. Returns a specific set of data in form of a
     * IUserData typed object.
     * <p>
     * Precondition: The user is logged in.
     *
     * @return the logged in user, typed as an IUserData object.
     */
    public IUserData getLoggedInUser() {
        return account.getLoggedInUser();
    }

    /**
     * Getter for a group the logged-in-user is a member of.
     * @param groupID the id of the group which is returned.
     * @return group with the provided groupID, wrapped in IGroupData type.
     * @throws Exception thrown if a group with the groupID couldn't be found in the user's group.
     */
    public IGroupData getGroup(String groupID) throws Exception {
        return account.getAssociatedGroupFromId(groupID);
    }

    public Set<IUserData> getContacts() {
        return account.getContacts();
    }

}
