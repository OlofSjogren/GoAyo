package com.goayo.debtify.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
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
    public void addUserToGroup() {

    }

    @Test
    public void removeUserFromGroup() {
    }

    @Test
    public void createDebt() {
    }

    @Test
    public void payOffDebt() {
    }
}