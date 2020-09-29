package com.goayo.debtify.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test class written for the mock database. This database was written to test the specification
 * documented in this repository for the mock database.
 *
 */

public class MockDatabaseTest {

    private IDatabase database;

    @Before
    public void setUp() throws Exception {
        database = new MockDatabase();
    }

    @Test
    public void getGroups() {
        Set<Group> groupSet = database.getGroups("0786458765");
        assert (groupSet.contains(database.getGroupFromId("4116c93e-5542-4b5c-8423-010a901abdce")));
        assert (groupSet.contains(database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a")));
        assert (groupSet.size() == 2);
    }

    @Test
    public void getGroupFromId() {
        assert (database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a").getGroupMembers().size() == 5);
        assert (database.getGroupFromId("4116c93e-5542-4b5c-8423-010a901abdce").getGroupMembers().size() == 3);
        assert (database.getGroupFromId("d467b5bc-5fa9-4ac2-890d-29a07803d484").getGroupMembers().size() == 3);
    }

    @Test
    public void getUser() {
        assert (database.getUser("0738980732").getName().equals("Alex Phu"));
    }

    @Test
    public void registerUser() {
        String testPhoneNumber = "0987123454";
        database.registerUser(testPhoneNumber, "Ralf Broberg", "flar");
        assert (database.getUser(testPhoneNumber).getName().equals("Ralf Broberg"));
    }

    @Test
    public void registerGroup() {
        Set<User> userToBeAdded = new HashSet<>();
        userToBeAdded.add(database.getUser("0701234546"));
        userToBeAdded.add(database.getUser("0738980732"));
        userToBeAdded.add(database.getUser("0733387676"));

        String groupName = "Testgroup";

        database.registerGroup()

    }

    @Test
    public void addDebt() {
    }

    @Test
    public void addContact() {
    }

    @Test
    public void removeContact() {
    }

    @Test
    public void addPayment() {
    }

    @Test
    public void addUserToGroup() {
    }

    @Test
    public void getUserToBeLoggedIn() {
    }

    @Test
    public void removeUserFromGroup() {

    }

    @Test
    public void getContactList() {
        assertTrue(database.getContactList("0701234546").contains(database.getUser("0738980732")));
        assertTrue(database.getContactList("0701234546").contains(database.getUser("0733387676")));

        assertTrue(database.getContactList("0786458765").contains(database.getUser("0701094578")));
        assertTrue(database.getContactList("0786458765").contains(database.getUser("0701234546")));

        assertTrue(database.getContactList("0738980732").contains(database.getUser("0786458765")));
        assertTrue(database.getContactList("0738980732").contains(database.getUser("0733387676")));

        assertTrue(database.getContactList("0701094578").contains(database.getUser("0701234546")));
        assertTrue(database.getContactList("0701094578").contains(database.getUser("0786458765")));
        assertTrue(database.getContactList("0701094578").contains(database.getUser("0738980732")));
        assertTrue(database.getContactList("0701094578").contains(database.getUser("0733387676")));

        assertTrue(database.getContactList("0701094578").isEmpty());


    }
}