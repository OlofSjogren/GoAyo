package com.goayo.debtify.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GroupTest {

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
    public void testAddMultipleUsers() {
        Group tempGroup = new Group("Belgien resa", "2345", userList);

        assertTrue(tempGroup.getGroupMembers().containsAll(userList));

        List<User> secondUserList = new ArrayList<>();
        secondUserList.add(new User("12345566", "Lars"));
        secondUserList.add(new User("987654", "Holme"));
        secondUserList.add(new User("12345566", "Bengt"));
        secondUserList.add(new User("987654", "Felix"));

        tempGroup.addUser(secondUserList);
        assertTrue(tempGroup.getGroupMembers().containsAll(secondUserList));
    }

    @Test
    public void testRemoveUser() {
        Group tempGroup = new Group("Tyskland resa", "1234", user);
        User testUser = new User("4321", "Åke");

        int sizebefore = tempGroup.getGroupMembers().size();
        tempGroup.addUser(testUser);
        assertTrue(tempGroup.getGroupMembers().contains(testUser));

        tempGroup.removeUser(testUser);
        int sizeAfter = tempGroup.getGroupMembers().size();
        assertEquals(sizebefore, sizeAfter);

        assertFalse(tempGroup.getGroupMembers().contains(testUser));
    }

    @Test
    public void testRemoveMultipleUsers() {
        Group tempGroup = new Group("Belgien resa", "2345", userList);

        List<User> secondUserList = new ArrayList<>();
        secondUserList.add(new User("12345566", "Lars"));
        secondUserList.add(new User("987654", "Holme"));
        secondUserList.add(new User("12345566", "Bengt"));
        secondUserList.add(new User("987654", "Felix"));

        tempGroup.addUser(secondUserList);
        tempGroup.removeUser(userList);

        for(User u: userList) {
            assertFalse(tempGroup.getGroupMembers().contains(u));
        }

        tempGroup.removeUser(secondUserList);
        assertTrue(tempGroup.getGroupMembers().isEmpty());
    }

    @Test
    public void testRemoveNonExistingUser() {
        Group tempGroup = new Group("Belgien resa", "2345", userList);

        List<User> secondUserList = new ArrayList<>();
        secondUserList.add(new User("12345566", "Lars"));
        secondUserList.add(new User("987654", "Holme"));
        secondUserList.add(new User("12345566", "Bengt"));
        secondUserList.add(new User("987654", "Felix"));

        assertFalse(tempGroup.removeUser(secondUserList));
    }
}