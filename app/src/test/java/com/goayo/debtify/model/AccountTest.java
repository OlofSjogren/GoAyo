package com.goayo.debtify.model;

import org.junit.BeforeClass;
import org.junit.Test;

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

        assert();
    }

    @Test
    public void createGroup() {
    }

    @Test
    public void addContact() {
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