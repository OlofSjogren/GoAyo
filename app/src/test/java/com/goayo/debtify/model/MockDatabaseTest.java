package com.goayo.debtify.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class written for the mock database. This database was written to test the specification
 * documented in this repository for the mock database.
 *
 */

public class MockDatabaseTest {

    @Before
    public void setUp() throws Exception {
        IDatabase database = new MockDatabase();
    }

    @Test
    public void getGroups() {
    }

    @Test
    public void getGroupFromId() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void registerUser() {
    }

    @Test
    public void registerGroup() {
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
    }
}