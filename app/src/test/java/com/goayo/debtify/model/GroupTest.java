package com.goayo.debtify.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class GroupTest {

    static Set<User> userSet;
    static User user;

    @BeforeClass
    public static void beforeClass() {
        user = new User("0760430079", "Karl");
        userSet = new HashSet<>();
        userSet.add(user);
        userSet.add(new User("12345566", "Sven"));
        userSet.add(new User("987654", "Anna"));
    }

    @Test
    public void testAddUser() throws UserAlreadyExistsException {
        Group tempGroup = new Group("Tyskland resa", "1234", user);
        User testUser = new User("4321", "Åke");
        int setSizeBefore = tempGroup.getGroupMembers().size();
        tempGroup.addUser(testUser);
        int setSizeAfter = tempGroup.getGroupMembers().size();
        assertTrue(setSizeBefore < setSizeAfter);
    }

    @Test
    public void testAddMultipleUsers() throws UserAlreadyExistsException {
        Group tempGroup = new Group("Belgien resa", "2345", userSet);

        assertTrue(tempGroup.getGroupMembers().containsAll(userSet));

        Set<User> secondUserList = new HashSet<>();
        secondUserList.add(new User("12345566", "Lars"));
        secondUserList.add(new User("987654", "Holme"));
        secondUserList.add(new User("12345566", "Bengt"));
        secondUserList.add(new User("987654", "Felix"));

        tempGroup.addUser(secondUserList);
        assertTrue(tempGroup.getGroupMembers().containsAll(secondUserList));
    }

    @Test
    public void testRemoveUser() throws UserAlreadyExistsException, UserNotFoundException {
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
    public void testRemoveMultipleUsers() throws UserAlreadyExistsException, UserNotFoundException {
        Group tempGroup = new Group("Belgien resa", "2345", userSet);

        Set<User> secondUserList = new HashSet<>();
        secondUserList.add(new User("12345566", "Lars"));
        secondUserList.add(new User("987654", "Holme"));
        secondUserList.add(new User("12345566", "Bengt"));
        secondUserList.add(new User("987654", "Felix"));

        tempGroup.addUser(secondUserList);
        tempGroup.removeUser(userSet);

        for (User u : userSet) {
            assertFalse(tempGroup.getGroupMembers().contains(u));
        }

        tempGroup.removeUser(secondUserList);
        assertTrue(tempGroup.getGroupMembers().isEmpty());
    }

    @Test
    public void testRemoveNonExistingUser() throws UserNotFoundException {
        Group tempGroup = new Group("Belgien resa", "2345", userSet);

        Set<User> secondUserList = new HashSet<>();
        secondUserList.add(new User("12345566", "Lars"));
        secondUserList.add(new User("987654", "Holme"));
        secondUserList.add(new User("12345566", "Bengt"));
        secondUserList.add(new User("987654", "Felix"));

        try {
            tempGroup.removeUser(secondUserList);
            fail();
        } catch (UserNotFoundException e) {
            assertTrue(true);
        }
    }
}