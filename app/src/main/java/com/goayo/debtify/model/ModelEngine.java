package com.goayo.debtify.model;

import com.goayo.debtify.MockDatabase.MockDatabase;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Set;

/**
 * @Author Oscar Sanner
 * @date 2020-09-15
 * <p>
 * A facade class for the model package. The purpose of the class is to be the face outwards towards other
 * packages depending on the model. This class aims to keep the model loosely coupled with other packages.
 * All direct interaction with the model is made through this class.
 * <p>
 * 2020-09-18 Modfied by Olof Sjögren, Yenan Wang and Alex Phu: removed booleans returns and replaced with exceptions.
 * 2020-09-21 Modified by Alex Phu and Oscar Sanner: Implemented leave and remove feature.
 * 2020-09-23 Modified by Olof Sjögren: Added getGroup-method provided a specific id.
 * 2020-09-28 Modified by Yenan Wang: refactor to add parameter description to createDebt method
 * 2020-09-28 Modified by Alex Phu: Refactored hardcoded debt data.
 * 2020-09-30 Modified by Oscar Sanner and Olof Sjögren: Added log out method.
 * 2020-09-30 Modified by Olof Sjögren and Oscar Sanner: Now implements IObservable and (for now) notifies on registration and login.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-07 Modified by Olof Sjögren and Oscar Sanner: No longer implements IObservable, EventBus is implemented.
 * 2020-10-09 Modified by Alex Phu and Yenan Wang: Added IDebtSplitStrategy to createDebt's parameter.
 * 2020-10-11 Modified by Alex Phu: Fixed wrong order of arguments in registerUser();
 * 2020-10-13 Modified by Olof Sjögren: Updated JDocs
 */

public class ModelEngine {

    private Account account;
    private static ModelEngine instance;

    private ModelEngine(Account account) {
        this.account = account;
    }

    /**
     * A standard singleton getInstance function.
     *
     * @return The single instance of the ModelEngine.
     */
    public static ModelEngine getInstance() {
        if (instance == null) {
            instance = new ModelEngine(new Account(new MockDatabase()));
        }
        return instance;
    }

    /**
     * Register a new user in the database
     * <p>
     * Precondition: The no user with the same phone number exists in the database.
     *
     * @param phoneNumber the phone number of the user to register.
     * @param name        the name of the user to register.
     * @param password    the passwords of the user to register.
     * @throws RegistrationException thrown if the registration failed.
     * @throws ConnectException      thrown if unable to make a database connection.
     */
    public void registerUser(String phoneNumber, String name, String password) throws RegistrationException, ConnectException {
        account.registerUser(phoneNumber, password, name);
    }

    /**
     * Method for force-updating the account towards the IDatabase.
     *
     * @throws Exception //TODO NEEEEEEEEDS TO SPECIFY
     */
    public void refreshWithDatabase() throws Exception {
        account.refreshWithDatabase();
    }

    /**
     * Logs the user into the application. Other functions related to account management will
     * throw an exception if no user is logged in.
     * <p>
     * Precondition: The user exists in the database.
     * Precondition: The password matches the user in the database.
     *
     * @param phoneNumber phone number used to find the User in the database.
     * @param password    password associated with the phone number.
     * @throws Exception //TODO NEEEEEEEEEEEEDS TO SPECIFY
     */
    public void logInUser(String phoneNumber, String password) throws Exception {
        account.loginUser(phoneNumber, password);
    }

    /**
     * Logs the current user out from the model and removes any personal contacts or groups
     * that are stored in the model.
     * <p>
     * Precondition: The user is logged in to the model.
     */
    public void logOutUser() {
        account.logOutUser();
    }

    /**
     * Adds a user with the specific phone number as a contact.
     *
     * @param phoneNumber the phone number of the user to be added, given that such a user exists in the database.
     * @throws UserNotFoundException      thrown if a user with the given phone number can't be found in the database.
     * @throws UserAlreadyExistsException thrown if a user with that phone number already is a contact of the logged in user.
     * @throws ConnectException           thrown if unable to make a database connection.
     */
    public void addContact(String phoneNumber) throws UserNotFoundException, UserAlreadyExistsException, ConnectException {
        account.addContact(phoneNumber);
    }


    /**
     * Removes a contact from the users contact book.
     * <p>
     * Precondition: The specified phone number exists in the users phone book.
     *
     * @param phoneNumber The phone number associated to the user that will be removed from
     *                    the contact book.
     * @throws Exception //TODO NEEEEEEDS TO SPECIFY
     */
    public void removeContact(String phoneNumber) throws Exception {
        account.removeContact(phoneNumber);
    }

    /**
     * Getter for a specific user from the database.
     *
     * @param phoneNumber the specific user's phone number. This will be used to find the user in the database.
     * @return returns a User object if it was found in the database. The User object is returned wrapped in a IUserData type.
     * @throws UserNotFoundException thrown if no user with the given phone number could be found in the database.
     * @throws ConnectException      thrown if unable to make a database connection.
     */
    public IUserData getSingleUserFromDatabase(String phoneNumber) throws UserNotFoundException, ConnectException {
        return account.getSingleUserFromDatabase(phoneNumber);
    }

    /**
     * Creates a new group, associated to the logged in user. The logged in user is
     * automatically added into the new group, along with the user associated to the list
     * of phone numbers.
     *
     * @param groupName      The name of the group.
     * @param phoneNumberSet A set of phone numbers associated with users who will be added
     *                       into the group.
     * @throws Exception //TODO NEEEEEEEEEEDS TO SPECIFY
     */
    public void createGroup(String groupName, Set<String> phoneNumberSet) throws Exception {
        account.createGroup(groupName, phoneNumberSet);
    }

    /**
     * De-associates the logged in user with a group.
     * <p>
     * Precondition: A user is logged in and in the group with the specified ID.
     *
     * @param groupID The ID specific to the group that the user will leave.
     * @throws Exception //TODO NEEEEEEEEEEEEEEEEDS TO SPECIFY
     */
    public void leaveGroup(String groupID) throws Exception {
        account.leaveGroup(groupID);
    }

    /**
     * Getter for the logged in users associated groups. (groups with membership)
     *
     * @return A set of the associated groups wrapped in the IGroupData type.
     */
    public Set<IGroupData> getGroups() {
        return account.getAssociatedGroups();
    }

    /**
     * Method for adding a user to a group, will be added both client side and in the database.
     * <p>
     * Precondition: The phone number is registered to a user in the database.
     * Precondition: A group with the given group id exists.
     * Precondition: The user is not already in the group with the given ID.
     *
     * @param phoneNumber the phonenumber of the user to add to the group.
     * @param groupID     the ID of the group into which the user will be added.
     * @throws Exception //TODO NEEEEEEEEEDS TO SPECIFY
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
     * @throws Exception //TODO NEEEEEEEEEEEEDS TO SPECIFY
     */
    public void removeUserFromGroup(String phoneNumber, String groupID) throws Exception {
        account.removeUserFromGroup(phoneNumber, groupID);
    }

    /**
     * Creates a new debt on the server as well as client side between one lender and one
     * or more borrowers. The debt is associated to a group. How the sum specified by the owed
     * parameter is split (if at all) between the borrowers is handled by the given splitStrategy.
     * <p>
     * Precondition: lender and borrower(s) exists in the database.
     * Precondition: The group ID exists in the database.
     * Precondition: Owed has a positive value.
     *
     * @param groupID       The ID of the group to which the debt is associated.
     * @param lender        The phone number of the user lending the money.
     * @param borrower      A list of one or more users.
     * @param owed          A positive BigDecimal, representing the whole value spent by the lender.
     * @param description   A short string, preferably <20 characters, that describes the debt
     * @param splitStrategy How the debt will be split (if at all) among the borrowers.
     * @throws Exception //TODO NEEEEEEEEEEEEDS TO SPECIFY
     */
    public void createDebt(String groupID, String lender, Set<String> borrower, BigDecimal owed, String description, IDebtSplitStrategy splitStrategy) throws Exception {
        account.createDebt(groupID, lender, borrower, owed, description, splitStrategy);
    }

    /**
     * Creates one payment towards a debt owed by the logged in user.
     * <p>
     * Precondition: The debt with the specified debt ID exists in the application.
     * Precondition: The payment is smaller than or equals to the whole value of the debt.
     * Precondition: The amount is a positive double.
     *
     * @param amount  The amount to pay off on the debt.
     * @param debtID  The ID of the debt towards which the payment will be done.
     * @param groupID The ID of the group in which the payment will be made.
     * @throws Exception //TODO NEEEEEEEEEDS TO SPECIFY
     */
    public void payOffDebt(BigDecimal amount, String debtID, String groupID) throws Exception {
        account.payOffDebt(amount, debtID, groupID);
    }

    /**
     * Getter for the logged in user.
     * <p>
     * Precondition: The user is logged in.
     *
     * @return the logged in user wrapped as in the IUserData type.
     */
    public IUserData getLoggedInUser() {
        return account.getLoggedInUser();
    }

    /**
     * Getter for a group the logged-in-user is a member of.
     *
     * @param groupID the id of the group which is returned.
     * @return group with the provided groupID, wrapped in IGroupData type.
     */
    public IGroupData getGroup(String groupID) {
        return account.getAssociatedGroupFromId(groupID);
    }

    /**
     * Getter for the logged in user's contact set.
     *
     * @return a set of the logged in user's contacts, each user is wrapped in IUserData type.
     */
    public Set<IUserData> getContacts() {
        return account.getContacts();
    }

}
