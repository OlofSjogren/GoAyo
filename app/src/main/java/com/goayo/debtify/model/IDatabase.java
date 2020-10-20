package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @Author Oscar Sanner
 * @date 2020-09-15
 * <p>
 * An interface implemented by database classes.
 * A database class is responsible for all the active instances of groups, as it acts as
 * a single source of truth.
 * <p>
 * 2020-09-28 Modified by Oscar Sanner and Olof Sjögren: Added new methods for all types of calls related to the database.
 * 2020-09-29 Modified by Oscar Sanner: Added small adjustments to the documentation. Specified precondition where needed.
 * 2020-09-30 Modified by Oscar Sanner and Olof Sjögren: Changed return type of registerUser, boolean to void.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Made package private.
 * 2020-10-12 Modified by Oscar Sanner: Added documentation for the specific json string.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs. Also removed some redundant boolean return values.
 * 2020-10-14 Modified by Oscar Sanner: Removed documentation for how specific JsonStrings will be formatted.
 */

public interface IDatabase {

    /**
     * A method that returns a Json-String of all groups containing the user with the given
     * phone number.
     * <p>
     * Post condition: The Json string will follow the format specified in the GroupArrayJsonString
     * documentation.
     *
     * @param phoneNumber The phone number of the user belonging to the sought groups.
     * @return A json file with the above specified properties of all the groups in which the user with the provided phone number is a member.
     * @throws UserNotFoundException thrown if a user with the given phoneNumber doesn't exist in the database.
     * @throws ConnectException      thrown if unable to make a database connection.
     */
    JsonString.GroupArrayJsonString getGroups(String phoneNumber) throws UserNotFoundException, ConnectException;

    /**
     * Returns a user with the given phone number.
     * <p>
     * Post condition: The Json string will follow the format specified in the UserJsonString
     * documentation.
     *
     * @param phoneNumber The phone number of the user.
     * @return A json file with the above specified properties the user with the specified phone number.
     * @throws UserNotFoundException thrown if a user with the given phone number doesn't exist in the database.
     * @throws ConnectException      thrown if unable to make a database connection.
     */
    JsonString.UserJsonString getUser(String phoneNumber) throws UserNotFoundException, ConnectException;

    /**
     * Register a new user in the database.
     *
     * @param phoneNumber The phone number of the new user.
     * @param password    The password of the new user.
     * @param name        The name of the new user.
     * @return true if the operation was successful. False if the phone number is already registered.
     * @throws ConnectException      thrown if unable to make a database connection.
     * @throws RegistrationException thrown if the registration of the user failed in the database.
     */
    void registerUser(String phoneNumber, String password, String name) throws ConnectException, RegistrationException;


    /**
     * Registers a new group in the database.
     *
     * @param name             The name of the group.
     * @param usersPhoneNumber A set with phone numbers of all the user to be registered in
     *                         the group.
     * @param id               the group id of the group to be registered.
     * @throws RegistrationException thrown if the registration of the group failed in the database.
     * @throws ConnectException      thrown if unable to make a database connection.
     */
    void registerGroup(String name, Set<String> usersPhoneNumber, String id) throws RegistrationException, ConnectException;

    /**
     * Adds a new debt in a group between two users.
     *
     * @param groupID       The id of the group in which the debt will be added.
     * @param lender        The phone number of the person lending the money out.
     * @param borrowers     A set of phone numbers of the users who borrows money.
     * @param amount        The total amount of the loan.
     * @param description   a description of the loan.
     * @param splitStrategy the strategy which determines how the debt will be split among the borrowers (if at all).
     * @param date          timestamp for creation of the debt.
     * @throws ConnectException       thrown if unable to make a database connection.
     * @throws GroupNotFoundException thrown if a group with the given group id can't be found in the database.
     */
    void addDebt(String groupID, String lender, Map<IUserData, String> borrowers, BigDecimal amount, String description, IDebtSplitStrategy splitStrategy, Date date) throws ConnectException, GroupNotFoundException, UserNotFoundException;

    /**
     * Adds a new contact to a users list of contacts.
     *
     * @param userPhoneNumber               Phone number of the user who is adding a contact.
     * @param phoneNumberOfContactToBeAdded Phone number of the user being added as a contact.
     * @throws UserNotFoundException thrown if either of the phone numbers given does not match an existing user in the database.
     * @throws ConnectException      thrown if unable to make a database connection.
     */
    void addContact(String userPhoneNumber, String phoneNumberOfContactToBeAdded) throws UserNotFoundException, ConnectException;

    /**
     * Removes a contact from a users contact list.
     *
     * @param userPhoneNumber                 The phone number of the user removing the contact.
     * @param phoneNumberOfContactToBeRemoved The phone number of the user being removed as a contact.
     * @throws UserNotFoundException thrown if either of the phone numbers given does not match an existing user in the database.
     * @throws ConnectException      thrown if unable to make a database connection.
     */
    void removeContact(String userPhoneNumber, String phoneNumberOfContactToBeRemoved) throws UserNotFoundException, ConnectException;

    /**
     * Adds a payment towards a debt.
     *
     * @param GroupID The id of the group to which the debt belongs to.
     * @param debtID  The id of the debt towards which the payment is made.
     * @param amount  The amount being payed towards the debt.
     * @param id      the generated id for the payment which is to be created.
     * @throws GroupNotFoundException  thrown if a group with the given group id can't be found in the database.
     * @throws InvalidDebtException    thrown if the id of the debt, towards which a payment is to be made, can't be found in the database.
     * @throws InvalidPaymentException thrown if the payment failed to be made in the database.
     * @throws ConnectException        thrown if unable to make a database connection.
     */
    void addPayment(String GroupID, String debtID, BigDecimal amount, String id, Date date) throws GroupNotFoundException, InvalidDebtException, InvalidPaymentException, ConnectException;

    /**
     * Adds a user to a specific group.
     *
     * @param groupID     The id of the group.
     * @param phoneNumber The id of the user.
     * @throws UserNotFoundException      thrown if unable to find a user with the given phonenumber in the database.
     * @throws GroupNotFoundException     thrown if unable to find a group with the given id in the database.
     * @throws ConnectException           thrown if unable to make a database connection.
     * @throws UserAlreadyExistsException thrown if the user which is to be added to the group already is a member of the group.
     */
    void addUserToGroup(String groupID, String phoneNumber) throws UserNotFoundException, GroupNotFoundException, ConnectException, UserAlreadyExistsException;

    /**
     * Checks if a password and a phone number matches, and returns the user with the provided
     * phone number if it does.
     * <p>
     * Post condition: The Json string will follow the format specified in the UserJsonString
     * documentation.
     *
     * @param phoneNumber The phone number of the user being logged in.
     * @param password    The password of the user being logged in.
     * @return The user with the provided phone number, as a json-string, if the password matches mentioned phone
     * number.
     * @throws LoginException   thrown if the attempt to login a user with the matching phone number and password failed.
     * @throws ConnectException thrown if unable to make a database connection.
     */
    JsonString.UserJsonString getUserToBeLoggedIn(String phoneNumber, String password) throws LoginException, ConnectException;

    /**
     * Getter for a list of users in an other users contact list.
     * <p>
     * Post condition: The Json string will follow the format specified in the UserArrayJsonString
     * documentation.
     *
     * @param phoneNumber The phone number of the user requesting the contact list.
     * @return A json file with the above specified format.
     * @throws UserNotFoundException thrown if a user with the given phone number can't be found in the database.
     * @throws ConnectException      thrown if unable to make a database connection.
     */
    JsonString.UserArrayJsonString getContactList(String phoneNumber) throws UserNotFoundException, ConnectException;

    /**
     * Removes a user from a group.
     *
     * @param phoneNumber The phone number of the user being removed.
     * @param groupID     The id of the group in which the user will be removed.
     * @throws UserNotFoundException  thrown if a user with the given phone number can't be found in the database.
     * @throws GroupNotFoundException thrown if a group with the given id can't be found in the database.
     * @throws ConnectException       thrown if unable to make a database connection.
     */
    void removeUserFromGroup(String phoneNumber, String groupID) throws UserNotFoundException, GroupNotFoundException, ConnectException;

}
