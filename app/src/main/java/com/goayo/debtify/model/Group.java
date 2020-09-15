package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Class representing group.
 */
public class Group implements IGroupData {

    private String groupName;
    private Ledger groupLedger;
    private final String groupId;
    private Set<User> groupMembers = new HashSet<>();

    public Group(String groupName, String groupId, HashSet<User> groupMembers) {
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

    @Override
    public String getGroupID() {
        return groupId;
    }

    @Override
    public Set<IUserData> getNamesAndPhonenumbersMap() {
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
