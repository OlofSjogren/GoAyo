package com.goayo.debtify.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class GroupTest {

    static Group group;
    static List<User> userList;
    static User user;

    @BeforeClass
    public static void beforeClass() {
        user = new User("0760430079", "Karl");
        userList = new ArrayList<>();
        userList.add(user);
        userList.add(new User("12345566", "Sven"));
        userList.add(new User("987654", "Anna"));
    }

    @Test
    public void testAddUser() {
        Group tempGroup = new Group("Tyskland resa", "1234", user);
        User testUser = new User("4321", "Åke");
        tempGroup.addUser(testUser);
        assertEquals(tempGroup.getGroupMembers().get(1), testUser);
    }

    @Test
    public void testRemoveUser() {
        Group tempGroup = new Group("Tyskland resa", "1234", user);
        User testUser = new User("4321", "Åke");
        tempGroup.addUser(testUser);
        tempGroup.removeUser(testUser);
        assertFalse(tempGroup.getGroupMembers().contains(testUser));
    }

    @Test
    public void testCreateDebt() {
    }

    @Test
    public void testPayOffDebt() {
    }
}