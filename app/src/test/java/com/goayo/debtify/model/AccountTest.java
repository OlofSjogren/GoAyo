package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IGroupData;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    static Account account;
    static MockDatabase database;

    @Before
    public void beforeClass() throws Exception {
        database = new MockDatabase();
        account = new Account(database);
        account.loginUser("0701234546", "racso");
    }

    @Test
    public void registerUser() throws Exception {
        account.registerUser("123", "asd", "Åke");
        assertEquals("Åke", database.getUser("123").getName());
    }

    @Test
    public void loginUser() {
        assert(account.getLoggedInUser() != null);
        assert(account.getContacts() != null);
        assert(account.getAssociatedGroups() != null);
    }

    @Test
    public void createGroup() throws Exception {
        int groupSizeBefore = account.getAssociatedGroups().size();
        Set<String> userToNewGroup = new HashSet<>();
        userToNewGroup.add("0738980732");
        account.createGroup("test", userToNewGroup);
        int groupSizeAfter = account.getAssociatedGroups().size();

        assertTrue(groupSizeAfter > groupSizeBefore);
    }

    @Test
    public void addContact() throws Exception {
        int contactSizeBefore = account.getContacts().size();
        account.addContact("0786458765");
        int contactSizeAfter = account.getContacts().size();
        assertTrue (contactSizeAfter > contactSizeBefore);
    }

    @Test
    public void addUserToGroup() throws Exception {

        Group g = database.getGroupFromId("4116c93e-5542-4b5c-8423-010a901abdce");
        assertFalse(g.getGroupMembers().contains(database.getUser("0733387676")));
        assertEquals(3, g.getGroupMembers().size());
        account.addUserToGroup("0733387676", "4116c93e-5542-4b5c-8423-010a901abdce");
        assertEquals(4, g.getGroupMembers().size());
        assertTrue(g.getGroupMembers().contains(database.getUser("0733387676")));
    }

    @Test
    public void removeUserFromGroup()  throws Exception{
        Group g = database.getGroupFromId("4116c93e-5542-4b5c-8423-010a901abdce");
        assertFalse(g.getGroupMembers().contains(database.getUser("0733387676")));
        assertEquals(3, g.getGroupMembers().size());
        account.addUserToGroup("0733387676", "4116c93e-5542-4b5c-8423-010a901abdce");
        assertEquals(4, g.getGroupMembers().size());
        assertTrue(g.getGroupMembers().contains(database.getUser("0733387676")));
        account.removeUserFromGroup("0733387676", "4116c93e-5542-4b5c-8423-010a901abdce");
        assertFalse(g.getGroupMembers().contains(database.getUser("0733387676")));
        assertEquals(3, g.getGroupMembers().size());
    }

    @Test
    public void leaveGroup() throws Exception {
        Set<IGroupData> groupsBefore = account.getAssociatedGroups();
        assertTrue(account.getAssociatedGroups().contains(database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a")));
        account.leaveGroup("1a705586-238d-4a29-b7af-36dc103bd45a");
        assertFalse(account.getAssociatedGroups().contains(database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a")));
        Set<IGroupData> groupsAfter = account.getAssociatedGroups();
        assertTrue(groupsBefore.size() > groupsAfter.size());
    }

    //TODO: Awaiting a more testable model.

    @Test
    public void removeContact() throws Exception {
        assertTrue(account.getContacts().contains(database.getUser("0733387676")));
        account.removeContact("0733387676");
        assertFalse(account.getContacts().contains(database.getUser("0733387676")));
    }



    @Test
    public void createDebt() {

    }

    @Test
    public void payOffDebt() {

    }
}