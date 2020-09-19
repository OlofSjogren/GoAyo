package com.goayo.debtify.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    static User user;

    @BeforeClass
    public static void beforeClass() {
        user = new User("0760430079", "Karl");
    }

    @Test
    public void testGetPhoneNumber() {
        assertEquals(user.getPhoneNumber(), "0760430079");
    }

    @Test
    public void testGetName() {
        assertEquals(user.getName(), "Karl");
    }
}

