package com.goayo.debtify.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
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

        //Adding the same user again should throw exception.
        Assert.assertThrows(UserAlreadyExistsException.class,
                () -> tempGroup.addUser(testUser)
        );
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

        for (User user : secondUserList) {
            Assert.assertThrows(UserAlreadyExistsException.class,
                    () -> tempGroup.addUser(user)
            );
        }
    }

    @Test
    public void testRemoveUser() throws UserAlreadyExistsException, UserNotFoundException {
        Group tempGroup = new Group("Tyskland resa", "1234", user);
        User testUser = new User("4321", "Åke");

        int sizebefore = tempGroup.getGroupMembers().size();
        tempGroup.addUser(testUser);
        assertTrue(tempGroup.getGroupMembers().contains(testUser));

        //Check that user already in group can't be added again, expect exception to be thrown.
        Assert.assertThrows(UserAlreadyExistsException.class,
                () -> tempGroup.addUser(testUser)
        );

        tempGroup.removeUser(testUser);
        int sizeAfter = tempGroup.getGroupMembers().size();
        assertEquals(sizebefore, sizeAfter);

        assertFalse(tempGroup.getGroupMembers().contains(testUser));

        User testUserNotInGroup = new User("070123456", "Kent");

        //Check that user not in group can't be removed from a group, expect exception to be thrown.
        Assert.assertThrows(UserNotFoundException.class,
                () -> tempGroup.removeUser(testUserNotInGroup)
        );
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

        for (User u : secondUserList) {

            //Check that user not in group can't be removed from a group, expect exception to be thrown.
            Assert.assertThrows(UserAlreadyExistsException.class,
                    () -> tempGroup.addUser(u)
            );
        }

        tempGroup.removeUser(userSet);

        for (User u : userSet) {
            assertFalse(tempGroup.getGroupMembers().contains(u));

            //Check that user not in group can't be removed from a group, expect exception to be thrown.
            Assert.assertThrows(UserNotFoundException.class,
                    () -> tempGroup.removeUser(u)
            );
        }

        tempGroup.removeUser(secondUserList);
        assertTrue(tempGroup.getGroupMembers().isEmpty());
    }

    @Test
    public void testGetUserTotal() throws DebtException, UserNotFoundException {
        User user1 = new User("0760460051", "Alex");
        User user2 = new User("0760460052", "Axel");
        Set<User> tempUserSet = new HashSet<>();
        tempUserSet.add(user1);
        tempUserSet.add(user2);

        Random rnd = new Random(System.nanoTime());
        int random_int = Math.abs(rnd.nextInt());

        Group tempGroup = new Group("PPY", "9876", tempUserSet);

        Map<User, String> map1 = new HashMap<>();
        map1.put(user1, "0760460051");
        tempGroup.createDebt(user1, map1, new BigDecimal(random_int), "Test", DebtSplitFactory.createNoSplitStrategy(), new Date());

        assertEquals(new BigDecimal(random_int).setScale(2, RoundingMode.HALF_EVEN), tempGroup.getUserTotal("0760460051"));
    }
}