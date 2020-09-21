package com.goayo.debtify.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MockDatabaseTest {

    IDatabase database;
    Set<Group> groups;
    User loggedInUser;
    User specificUser;

    @Before
    public void setUp() throws Exception {
        database = new MockDatabase();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getGroups() {
        groups = database.getGroups("0934765893");
        assert(groups != null);
    }

    @Test
    public void getUser() {
        specificUser = database.getUser("0734355982");
        assert(specificUser != null);
    }

    @Test
    public void registerUser() {
        boolean success = database.registerUser("1234","123", "bo");
        boolean fail = database.registerUser("1234","123", "bo");

        assert(!fail);
        assert(success);
    }

    @Test
    public void registerGroup() {
        Group g = null;
        database.registerGroup("testgroup", new HashSet<User>());
        for(Group group : database.getGroups("1")){
            if(group.getGroupName() == "testgroup"){
                g = group;
            }
        }
        assert(g != null);
    }

    @Test
    public void getUserToBeLoggedIn() {
        loggedInUser = database.getUserToBeLoggedIn("1","1");
        assert(loggedInUser.getName().equals("Rolf Broberg"));
    }

    @Test
    public void getContactList() {
        Set<User> contacts = database.getContactList("1");
        assertEquals(4, contacts.size());
    }
}