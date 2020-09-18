package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class AccountTest {

    static Account account;
    static MockDatabase database;

    @BeforeClass
    public static void beforeClass() throws Exception {
        database = new MockDatabase();
        account = new Account(database);
        account.loginUser("123", "asd");
    }

    @Test
    public void registerUser() throws Exception {
        account.registerUser("123", "Åke", "asd");
        assertEquals("Åke", database.getUser("123").getName());
    }

    @Test
    public void loginUser() throws Exception {
        account.loginUser("123", "123");
        assert(account.getLoggedInUser() != null);
        assert(account.getContacts() != null);
        assert(account.getAssociatedGroups() != null);
    }

    @Test
    public void createGroup() throws Exception {
        int groupSizeBefore = account.getAssociatedGroups().size();
        Set<String> userToNewGroup = new HashSet<>();
        userToNewGroup.add("0945837563");
        account.createGroup("test", userToNewGroup);
        int groupSizeAfter = account.getAssociatedGroups().size();

        assert(groupSizeAfter > groupSizeBefore);
    }

    @Test
    public void addContact() throws Exception {
        int contactSizeBefore = account.getContacts().size();
        account.addContact("0704345621");
        int contactSizeAfter = account.getContacts().size();

        assert (contactSizeAfter > contactSizeBefore);
    }

    @Test
    public void addUserToGroup() throws Exception {
        IGroupData g;
        //
        int i = 1002;
        List<Integer> beforeSizes = new ArrayList<>();
        List<Integer> afterSizes = new ArrayList<>();
        List<Integer> assertionAtOne = new ArrayList<>();

        for(IGroupData group : account.getAssociatedGroups()){
            beforeSizes.add(group.getIUserDataSet().size());
        }

        for(IGroupData groupData : account.getAssociatedGroups()){
            account.addUserToGroup("0876123221", Integer.toString(i));
            i++;
        }

        for(IGroupData group : account.getAssociatedGroups()){
            afterSizes.add(group.getIUserDataSet().size());
        }

        for(int t = 0; t < beforeSizes.size(); t++){
            assertionAtOne.add((afterSizes.get(t)) - beforeSizes.get(t));
        }

        for (Integer integer : assertionAtOne){
            assert(integer == 1);
        }
    }

    @Test
    public void removeUserFromGroup()  throws Exception{
        int sizeBefore = 0;
        int sizeAfter = 0;

        for(IGroupData g : account.getAssociatedGroups()){
            if(g.getGroupID() == "1003"){
                sizeBefore = g.getIUserDataSet().size();
            }
        }
        account.removeUserFromGroup("0735216752", "1003");
        for(IGroupData g : account.getAssociatedGroups()){
            if(g.getGroupID() == "1003"){
                sizeAfter = g.getIUserDataSet().size();
            }
        }
        assert(sizeAfter < sizeBefore);
    }

    //TODO: Awaiting a more testable model.
    
    @Test
    public void createDebt() {

    }

    @Test
    public void payOffDebt() {

    }
}