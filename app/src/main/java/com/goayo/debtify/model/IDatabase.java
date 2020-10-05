package com.goayo.debtify.model;

import java.util.Set;

/**
 * @Author Oscar Sanner
 * @date 2020-09-15
 * <p>
 * Modified 2020-09-28 Oscar Olof.
 *
 * An interface implemented by database classes.
 *
 * A database class is responsible for all the active instances of groups, as it acts as
 * a single source of truth.
 *
 * 2020-09-28 Modified by Oscar Sanner and Olof Sjögren: Added new methods for all types of calls
 * related to the database.
 *
 * 2020-09-29 Modified by Oscar Sanner: Added small adjustments to the documentation. Specified
 * precondition where needed.
 *
 * 2020-09-30 Modified by Oscar Sanner and Olof Sjögren: Changed return type of registerUser.
 * boolean to void.
 *
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Made package private.
 */

interface IDatabase {
    /**
     * A method that returns a set of all groups containing the user with the given
     * phone number.
     *
     * @param phoneNumber The phone number of the user belonging to the sought groups.
     * @return A set of all the groups in which the user with the provided phone number is a member.
     */
    Set<Group> getGroups(String phoneNumber);

    /**
     * Returns a single group with the given id.
     *
     * @param groupID The id of the sought group.
     * @return The group with the given id.
     */
    Group getGroupFromId(String groupID);

    /**
     * Returns a user with the given phone number.
     *
     * @param phoneNumber The phone number of the user.
     * @return The user with the phone number.
     */
    User getUser(String phoneNumber);

    /**
     * Register a new user in the database.
     *
     * @param phoneNumber The phone number of the new user.
     * @param password The password of the new user.
     * @param name The name of the new user.
     * @return true if the operation was successful. False if the phone number is already
     *         registered.
     */
    void registerUser(String phoneNumber, String password, String name) throws UserAlreadyExistsException;

    /**
     * Registers a new group in the database.
     *
     * @param name The name of the group.
     * @param usersPhoneNumber A set with phone numbers of all the user to be registered in
     *                         the group.
     * @return True after a successful registration.
     */
    boolean registerGroup(String name, Set<String> usersPhoneNumber);

    /**
     * Adds a new debt in a group between two users.
     *
     * Postcondition: Does not promise to modify the same java objects as those held by the model.
     *                Only promises to modify the database. Groups should be re-fetched after
     *                calling this method.
     *
     * @param groupID The id of the group in which the debt will be added.
     * @param lender The phone number of the person lending the money out.
     * @param borrowers A set of phone numbers of user who borrows money.
     * @param amount The total amount of the loan.
     * @return True if the operation was successful, false if the all users as well as the group
     *         doesn't exist.
     * @throws Exception if something goes wrong when connecting to the server.
     */
    boolean addDebt(String groupID, String lender, Set<String> borrowers, double amount, String description) throws Exception;

    /**
     * Add a new contact to a users list of contacts.
     *
     * Postcondition: Does not promise to modify the same java objects as those held by the model.
     *                Only promises to modify the database. Contact list should be re-fetched after
     *                calling this method.
     *
     * @param userPhoneNumber Phone number of the user who is adding a contact.
     * @param contactToBeAdded Phone number of the user being added as a contact.
     * @return True if both of the users exits and the operation was successful, otherwise false.
     */
    boolean addContact(String userPhoneNumber, String contactToBeAdded);

    /**
     * Remove a contact from a users contact list.
     *
     * Postcondition: Does not promise to modify the same java objects as those held by the model.
     *                Only promises to modify the database. Contact list should be re-fetched after
     *                calling this method.
     *
     * @param userPhoneNumber The phone number of the user removing the contact.
     * @param phoneNumberOfContactToBeRemoved The phone number of the user being removed as a contact.
     * @return True if both of the users exits and the operation was successful, otherwise false.
     */
    boolean removeContact(String userPhoneNumber, String phoneNumberOfContactToBeRemoved);

    /**
     * Adds a payment towards a debt.
     *
     * Postcondition: Does not promise to modify the same java objects as those held by the model.
     *                Only promises to modify the database. Groups should be re-fetched after
     *                calling this method.
     *
     * @param GroupID The id of the group to which the debt belongs to.
     * @param debtID The id of the debt towards which the payment is made.
     * @param amount The amount being payed towards the debt.
     * @return True if the entity with the provided ids exist. Otherwise false.
     * @throws Exception if something goes wrong when connecting to the server.
     */
    boolean addPayment(String GroupID, String debtID, double amount) throws Exception;

    /**
     * Adds a user to a specific group.
     *
     * Postcondition: Does not promise to modify the same java objects as those held by the model.
     *                Only promises to modify the database. Groups should be re-fetched after
     *                calling this method.
     *
     * @param groupID The id of the group.
     * @param phoneNumber The id of the user.
     * @return True if the group and the user exists, and if the user is not already in the group.
     *         Otherwise false.
     */
    boolean addUserToGroup(String groupID, String phoneNumber);

    /**
     * Checks if a password and a phone number matches, and returns the user with the provided
     * phone number if it does.
     *
     * @param phoneNumber The phone number of the user being logged in.
     * @param password The password of the user being logged in.
     * @return The user with the provided phone number, if the password matches mentioned phone
     *         number.
     */
    User getUserToBeLoggedIn(String phoneNumber, String password);

    /**
     * Getter for a list of users in an other users contact list.
     *
     * @param phoneNumber The phone number of the user requesting the contact list.
     * @return A set of users in the contact list of the user with the provided phone number.
     */
    Set<User> getContactList(String phoneNumber);

    /**
     * Removes a user from a group.
     *
     * Postcondition: Does not promise to modify the same java objects as those held by the model.
     *                Only promises to modify the database. Groups should be re-fetched after
     *                calling this method.
     *
     * @param phoneNumber The phone number of the user being removed.
     * @param groupID The id of the group in which the user will be removed.
     * @return True on successful operation. False if user or group doesn't exist.
     */
    boolean removeUserFromGroup(String phoneNumber, String groupID);
}
