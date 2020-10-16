package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Class representing a group.
 * <p>
 * 2020-09-17 Modified by Alex Phu and Olof Sjögren: Changed List type to Set in method parameters.
 * 2020-09-16 Modified by Gabriel Brattgård and Yenan Wang: Added real ledger. Changed types on getDebts. Delegated to ledger.
 * 2020-09-18 Modified by Oscar Sanner and Alex Phu: Switched over List types to Set, also added JDocs and switch boolean returns to exceptions.
 * 2020-09-28 Modified by Yenan Wang: refactor to add parameter description to createDebt method
 * 2020-09-29 Modified by Olof Sjögren and Oscar Sanner: Added method call in removeUser() to also remove all debts associated to that user in the group.
 * 2020-09-29 Modified by Olof Sjögren and Oscar Sanner: Fixed bug where one of the constructors wouldn't initiate the ledger.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-09 Modified by Alex Phu and Yenan Wang: Added IDebtSplitStrategy to createDebt's parameter.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 * 2020-10-15 Modified by Yenan Wang & Alex Phu: implemented compareTo(..) method
 * 2020-10-16 Modified by Oscar Sanner: A debt now takes in a date on creation instead of creating on itself.
 * This will further persistence. This applies to the create debt method. The same however also applies to payments.
 */
class Group implements IGroupData {

    private final String groupName;
    private final Ledger groupLedger;
    private final String groupId;
    private Set<User> groupMembers = new HashSet<>();

    /**
     * Constructor for Group object, this one is for multiple users added on initialization.
     *
     * @param groupName    name of group.
     * @param groupId      unique string for group identification.
     * @param groupMembers set of users to be added.
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
     * @param groupName name of group.
     * @param groupId   unique string for group identification.
     * @param user      creator of the group.
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
     * @param newUser the single user to add as a group member.
     */
    public void addUser(User newUser) {
        groupMembers.add(newUser);
    }

    /**
     * Adding multiple users to the group as group members.
     *
     * @param newUsers set of the users to add.
     */
    public void addUser(Set<User> newUsers) {
        groupMembers.addAll(newUsers);
    }

    /**
     * Removing a single user from group and all debts associated to it in the group.
     *
     * @param removeUser single user to remove.
     */
    public void removeUser(User removeUser) {
        groupLedger.removeSpecificUserDebt(removeUser);
        groupMembers.remove(removeUser);
    }

    /**
     * Removing multiple users from group and all debts associated to them in the group.
     *
     * @param removeUsers multiple users to remove.
     */
    public void removeUser(Set<User> removeUsers) {
        for (User u : removeUsers) {
            groupLedger.removeSpecificUserDebt(u);
        }
        groupMembers.removeAll(removeUsers);
    }


    /**
     * Creates a debtTracker and adds it to the list of debtTrackers.
     *
     * @param lender        the user who lends out money
     * @param borrowers     either a single or several users who borrow from the lender
     * @param owed          total amount lent out by the lender to the borrowers
     * @param description   the brief description of the debt
     * @param splitStrategy How the debt is split
     * @param date
     */
    // TODO: Specify exception(?).
    public void createDebt(User lender, Map<User, String> borrowers, BigDecimal owed, String description, IDebtSplitStrategy splitStrategy, Date date) {
        groupLedger.createDebt(lender, borrowers, owed, description, splitStrategy, date);
    }

    /**
     * Adds a new payment to a specific debtTracker.
     *
     * @param amount        Amount being paid back against the debt.
     * @param debtTrackerID ID used to retrieve the specific debtTracker.
     */
    // TODO: Specify exception(?).
    public void payOffDebt(BigDecimal amount, String debtTrackerID, Date date) {
        groupLedger.payOffDebt(amount, debtTrackerID, date);
    }

    /**
     * @return a new set which contains the members of the group.
     */
    public Set<User> getGroupMembers() {
        return new HashSet<>(groupMembers);
    }


    @Override
    public String getGroupID() {
        return groupId;
    }

    /**
     * @return a new set which contains the members of the group, each member is wrapped in the IUserData type.
     */
    @Override
    public Set<IUserData> getIUserDataSet() {
        return new HashSet<>(groupMembers);
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    /**
     * @return a new ArrayList with the DebtTrackers associated with this groups ledger. Every DebtTracker is wrapped in the IDebtData type.
     */
    @Override
    public List<IDebtData> getDebts() {
        return new ArrayList<>(groupLedger.getDebtDataList());
    }

    /**
     * Fetches and calculates the user with the given phone number remaining total debt.
     *
     * @param phoneNumber the phonenumber of the User in group which remainning tatal is returned.
     * @return the remaining total debt of the given user.
     * Positive if user is owed more money than the user owes money and negative if vice versa.
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

    @Override
    public int compareTo(IGroupData group) {
        return groupName.compareTo(group.getGroupName());
    }
}
