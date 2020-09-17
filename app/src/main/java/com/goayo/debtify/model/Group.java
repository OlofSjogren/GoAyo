package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Class representing group.
 *
 * 2020-09-17 Modified by Alex Phu and Olof Sjögren: Changed List type to Set in method parameters.
 */
public class Group implements IGroupData {

    private String groupName;
    private Ledger groupLedger;
    private final String groupId;
    private Set<User> groupMembers = new HashSet<>();

    /**
     * Constructor for Group object, this one is for multiple users added on initialization.
     * @param groupName: name of group.
     * @param groupId: unique string for group identification.
     * @param groupMembers: set of users to be added.
     */
    public Group(String groupName, String groupId, Set<User> groupMembers) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupMembers = groupMembers;
    }

    /**
     * Constructor for Group object, this one is for no users added on initialization except creator.
     * @param groupName: name of group.
     * @param groupId: unique string for group identification.
     * @param user: creator of the group.
     */
    public Group(String groupName, String groupId, User user) {
        this.groupName = groupName;
        this.groupId = groupId;
        groupMembers.add(user);
    }

    /**
     * Adding a single user to group.
     * @param newUser single user to add.
     * @return Returns true if successfully added the user to the grouplist, otherwise returns false.
     */
    public boolean addUser(User newUser) {
        return groupMembers.add(newUser);
    }

    /**
     * Adding multiple users to group.
     * @param newUsers multiple users to add.
     * @return Returns true if successfully added all the users to the grouplist, otherwise returns false.
     */
    public boolean addUser(Set<User> newUsers) {
        return groupMembers.addAll(newUsers);
    }

    /**
     * Removing a single user from group.
     * @param removeUser single user to remove.
     * @return Returns true if successfully removed the user from the grouplist, otherwise returns false.
     */
    public boolean removeUser(User removeUser) {
        return groupMembers.remove(removeUser);
    }

    /**
     * Removing multiple users from group.
     * @param removeUsers multiple users to remove.
     * @return Returns true if successfully removed the all users from the grouplist, otherwise returns false.
     */
    public boolean removeUser(Set<User> removeUsers) {
        return groupMembers.removeAll(removeUsers);
    }

    public boolean createDebt(User lender, Set<User> borrower, double owed) {
        return groupLedger.createDebt(lender, borrower, owed);
    }

    public boolean payOffDebt(double amount, String debtID) {
        return groupLedger.payOffDebt(amount, debtID);
    }

    public Set<User> getGroupMembers() {
        return new HashSet<>(groupMembers);
    }

    @Override
    public String getGroupID() {
        return groupId;
    }

    @Override
    public Set<IUserData> getIUserDataSet() {
        return new HashSet<IUserData>((Set<? extends IUserData>) groupMembers);
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public Set<IDebtData> getDebts() {
        return new HashSet<IdebtData>(groupMembers);
    }
}
