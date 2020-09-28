package com.goayo.debtify.model;

import java.util.Set;

/**
 * @Author Oscar Sanner
 * @date 2020-09-15
 * <p>
 * Modified 2020-09-28 Oscar Olof.
 *
 * An interface implemented by database classes.
 */

public interface IDatabase {
    Set<Group> getGroups(String phoneNumber);
    Group getGroupFromId(String groupID);
    User getUser(String phoneNumber) throws Exception;
    boolean registerUser(String phoneNumber, String password, String name);
    boolean registerGroup(String name, Set<String> usersPhoneNumber);
    boolean addDebt(String groupID, String lender, Set<String> borrowers, double amount);
    boolean addContact(String userPhoneNumber, String contactToBeAdded);
    boolean removeContact(String userPhoneNumber, String phoneNumberOfContactToBeRemoved);
    boolean addPayment(String GroupID, String debtID, double amount);
    boolean addUserToGroup(String groupID, String phoneNumber);
    User getUserToBeLoggedIn(String phoneNumber, String password);
    Set<User> getContactList(String phoneNumber);
    boolean removeUserFromGroup(String phoneNumber, String groupID);
}
