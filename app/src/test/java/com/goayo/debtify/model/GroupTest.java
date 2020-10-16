package com.goayo.debtify.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


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
    public void testAddUser() {
        Group tempGroup = new Group("Tyskland resa", "1234", user);
        User testUser = new User("4321", "Åke");
        int setSizeBefore = tempGroup.getGroupMembers().size();
        tempGroup.addUser(testUser);
        int setSizeAfter = tempGroup.getGroupMembers().size();
        assertTrue(setSizeBefore < setSizeAfter);
    }

    @Test
    public void testAddMultipleUsers() {
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
    public void testRemoveNonExistingUser() {
        Group tempGroup = new Group("Belgien resa", "2345", userSet);

        Set<User> secondUserList = new HashSet<>();
        secondUserList.add(new User("12345566", "Lars"));
        secondUserList.add(new User("987654", "Holme"));
        secondUserList.add(new User("12345566", "Bengt"));
        secondUserList.add(new User("987654", "Felix"));

        tempGroup.removeUser(secondUserList);

        for (User u : secondUserList) {
            assertFalse(tempGroup.getGroupMembers().contains(u));
        }
    }

    @Test
    public void testGetUserTotal() {
        User user1 = new User("0760460051", "Alex");
        User user2 = new User("0760460052", "Axel");
        Set<User> tempUserSet = new HashSet<>();
        tempUserSet.add(user1);
        tempUserSet.add(user2);

        Random rnd = new Random(System.nanoTime());
        int random_int = rnd.nextInt();

        Group tempGroup = new Group("PPY", "9876", tempUserSet);

        Map<User, String> map1 = new HashMap<>();
        map1.put(user1, "0760460051");
        tempGroup.createDebt(user1, map1, new BigDecimal(random_int), "Test", DebtSplitFactory.createNoSplitStrategy());
        try {
            assertEquals(new BigDecimal(random_int), tempGroup.getUserTotal("0760460051"));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}