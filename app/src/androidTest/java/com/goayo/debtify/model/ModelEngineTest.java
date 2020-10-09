package com.goayo.debtify.model;

import com.goayo.debtify.Database.RealDatabase;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.ConnectException;
import java.util.HashMap;

public class ModelEngineTest {

    static HashMap<String, String> users = new HashMap<>();
    static RealDatabase database = new RealDatabase();

    @BeforeClass
    public static void beforeClass() throws RegistrationException, ConnectException {

    }

    @Test
    public void registerUser() throws RegistrationException, ConnectException {
        for (int i = 0; i < 99; i++){
            users.put("123" + i ,"000000000" + i );
            database.registerUser("123" + i , "000000000" + i, "TestUser" + i);
        }
    }

    @Test
    public void logInUser() {
    }

    @Test
    public void logOutUser() {
    }

    @Test
    public void addContact() {
    }

    @Test
    public void removeContact() {
    }

    @Test
    public void createGroup() {
    }

    @Test
    public void leaveGroup() {
    }

    @Test
    public void getGroups() {
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

    @Test
    public void getLoggedInUser() {
    }

    @Test
    public void getGroup() {
    }

    @Test
    public void getContacts() {
    }
}