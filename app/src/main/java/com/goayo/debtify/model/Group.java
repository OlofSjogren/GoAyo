package com.goayo.debtify.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Class representing user.
 */
public class Group {

    private String groupName;
    private Ledger groupLedger;
    private final String groupId;
    private List<User> groupMembers;

    public Group(String groupName, String groupId, List<User> groupMembers) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupMembers = groupMembers;
    }

    public Group(String groupName, String groupId, User user) {
        this.groupName = groupName;
        this.groupId = groupId;
        groupMembers.add(user);
    }

    public boolean addUser(User newUser) {
        return groupMembers.add(newUser);
    }

    public boolean addUser(List<User> newUsers) {
        return groupMembers.addAll(newUsers);
    }

    public boolean removeUser(User removeUser) {
        return groupMembers.remove(removeUser);
    }

    public boolean removeUser(List<User> removeUsers) {
        return groupMembers.removeAll(removeUsers);
    }

    public boolean createDebt(User lender, List<User> borrower, double owed) {
        return groupLedger.createDebt(lender, borrower, owed);
    }

    public boolean payOffDebt(double amount, String debtID) {
        return groupLedger.payOffDebt(amount, debtID);
    }

    public List<User> getGroupMembers() {
        return new ArrayList<>(groupMembers);
    }
}
