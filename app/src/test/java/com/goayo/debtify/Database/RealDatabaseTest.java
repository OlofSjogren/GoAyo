package com.goayo.debtify.Database;

import com.goayo.debtify.model.GroupNotFoundException;
import com.goayo.debtify.model.IDatabase;
import com.goayo.debtify.model.RegistrationException;
import com.goayo.debtify.model.UserNotFoundException;

import org.junit.Test;

import java.net.ConnectException;

import static org.junit.Assert.*;

public class RealDatabaseTest {

    @Test
    public void getGroups() throws UserNotFoundException, ConnectException {
        IDatabase database = new RealDatabase();
        String s = database.getGroups("0734266227");
        System.out.println(s);
    }

    @Test
    public void getGroupFromId() throws GroupNotFoundException, ConnectException {
        IDatabase database = new RealDatabase();
        String s = database.getGroupFromId("b4078830-549c-4f91-a2c1-dd0c54c0053c");
        //System.out.println(s);
    }

    @Test
    public void getUser() throws UserNotFoundException, ConnectException {
        IDatabase database = new RealDatabase();
        String s = database.getUser("9067890987");
        //System.out.println(s);
    }

    @Test
    public void registerUser() throws RegistrationException, ConnectException {
        IDatabase database = new RealDatabase();
        database.registerUser("9067890987", "123", "TestUser");
        //System.out.println(s);
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
    public void getContactList() {
    }

    @Test
    public void removeUserFromGroup() {
    }
}