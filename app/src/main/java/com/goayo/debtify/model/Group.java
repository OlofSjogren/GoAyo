package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Class representing group.
 * <p>
 * 2020-09-17 Modified by Alex Phu and Olof Sjögren: Changed List type to Set in method parameters.
 * 2020-09-16 Modified by Gabriel & Yenan : Added real ledger. Changed types on getDebts. Delegated to ledger.
 * 2020-09-18 Modified by Oscar & Alex : Switched over List types to Set, also added JDocs and switch boolean returns to exceptions.
 * 2020-09-28 Modified by Yenan: refactor to add parameter description to createDebt method
 * 2020-09-29 Modified by Olof & Oscar : Added method call in removeUser() to also remove all debts associated to that user in the group.
 * 2020-09-29 Modified by Olof & Oscar : Fixed bug where one of the constructors wouldn't initiate the ledger.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 *
 */
class Group implements IGroupData {

    private String groupName;
    private Ledger groupLedger;
    private final String groupId;
    private Set<User> groupMembers = new HashSet<>();

    /**
     * Constructor for Group object, this one is for multiple users added on initialization.
     *
     * @param groupName:    name of group.
     * @param groupId:      unique string for group identification.
     * @param groupMembers: set of users to be added.
     */
    public Group(String groupName, String groupId, Set<User> groupMembers) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupMembers = new HashSet<>(groupMembers);
        this.groupLedger = new Ledger();
    }

    /**
     * Constructor for Group object, this one is for no users added on initialization except creator.
     *
     * @param groupName: name of group.
     * @param groupId:   unique string for group identification.
     * @param user:      creator of the group.
     */
    public Group(String groupName, String groupId, User user) {
        this.groupName = groupName;
        this.groupId = groupId;
        groupMembers.add(user);
        this.groupLedger = new Ledger();
    }

    /**
     * Adding a single user to group.
     *
     * @param newUser single user to add.
     * @return Returns true if successfully added the user to the grouplist, otherwise returns false.
     */
    public boolean addUser(User newUser) {
        return groupMembers.add(newUser);
    }

    /**
     * Adding multiple users to group.
     *
     * @param newUsers multiple users to add.
     * @return Returns true if successfully added all the users to the grouplist, otherwise returns false.
     */
    public boolean addUser(Set<User> newUsers) {
        return groupMembers.addAll(newUsers);
    }

    /**
     * Removing a single user from group and all debts associated to it in the group.
     *
     * @param removeUser single user to remove.
     * @return Returns true if successfully removed the user from the grouplist, otherwise returns false.
     */
    public boolean removeUser(User removeUser) {
        groupLedger.removeSpecificUserDebt(removeUser);
        return groupMembers.remove(removeUser);
    }

    /**
     * Removing multiple users from group and all debts associated to them in the group.
     *
     * @param removeUsers multiple users to remove.
     * @return Returns true if successfully removed the all users from the grouplist, otherwise returns false.
     */
    public boolean removeUser(Set<User> removeUsers) {
        for (User u : removeUsers){
            groupLedger.removeSpecificUserDebt(u);
        }
        return groupMembers.removeAll(removeUsers);
    }

    /**
     * Creates a debtTracker and adds it to the list of debtTrackers.
     *
     * @param lender    the user who lends out money
     * @param borrowers either a single or several users who borrow from the lender
     * @param owed      total amount lent out by the lender to the borrowers
     * @param description the brief description of the debt
     * @throws Exception
     */
    // TODO: Specify exception.
    public void createDebt(User lender, Map<User, String> borrowers, BigDecimal owed, String description) {
        groupLedger.createDebt(lender, borrowers, owed, description);
    }

    /**
     * Adds a new payment to a specific debtTracker.
     *
     * @param amount        Amount being paid back against the debt.
     * @param debtTrackerID ID used to retrieve the specific debtTracker.
     * @throws
     */
    // TODO: Specify exception.
    public void payOffDebt(BigDecimal amount, String debtTrackerID) {
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

    /**
     * Fetches and calculates the user with the given phone number remaining total debt.
     *
     * @param phoneNumber the phonenumber of the User in group which remainning tatal is returned.
     * @return the remaining total debt. Calcultated according to: totalOwed - totalDebt
     * @throws UserNotFoundException thrown if a User with phoneNumber can't be found in the group.
     */
    @Override
    public BigDecimal getUserTotal(String phoneNumber) throws UserNotFoundException {
        User user = null;
        for (User member : groupMembers) {
            if (member.getPhoneNumber().equals(phoneNumber)) {
                user = member;
            }
        }
        if (user == null) {
            throw new UserNotFoundException("A User with that phonenumber doesn't exist in the group.");
        }
        return groupLedger.getUserTotal(user);
    }


}
