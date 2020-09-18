package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Class representing group.
 * 2020-09-16 Modified by Gabriel & Yenan : Added real ledger. Changed types on getDebts. Delegated to ledger.
 * 2020-09-18 Modified by Oscar & Alex : Switched over List types to Set, also added JDocs and switch boolean returns to exceptions.
 */
class Group implements IGroupData {

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
        this.groupLedger = new Ledger();
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

    /**
     * Creates a debtTracker and adds it to the list of debtTrackers.
     *
     * @param lender the user who lends out money
     * @param borrowers either a single or several users who borrow from the lender
     * @param owed total amount lent out by the lender to the borrowers
     * @throws Exception
     */
    // TODO: Specify exception.
    public void createDebt(User lender, Set<User> borrowers, double owed) throws Exception {
        groupLedger.createDebt(lender, borrowers, owed);
    }

    /**
     * Adds a new payment to a specific debtTracker.
     *
     * @param amount Amount being paid back against the debt.
     * @param debtTrackerID ID used to retrieve the specific debtTracker.
     * @throws
     */
    // TODO: Specify exception.
    public void payOffDebt(double amount, String debtTrackerID) throws Exception {
        groupLedger.payOffDebt(amount, debtTrackerID);
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
        return new HashSet<IUserData>(groupMembers);
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public List<IDebtData> getDebts() {
        return new ArrayList<>(groupLedger.getDebtDataList());
    }
}
