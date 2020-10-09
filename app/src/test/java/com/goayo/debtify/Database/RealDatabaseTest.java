package com.goayo.debtify.Database;

import com.goayo.debtify.model.GroupNotFoundException;
import com.goayo.debtify.model.IDatabase;
import com.goayo.debtify.model.InvalidDebtException;
import com.goayo.debtify.model.InvalidPaymentException;
import com.goayo.debtify.model.LoginException;
import com.goayo.debtify.model.RegistrationException;
import com.goayo.debtify.model.UserAlreadyExistsException;
import com.goayo.debtify.model.UserNotFoundException;
import com.google.gson.Gson;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RealDatabaseTest {

    static IDatabase database;
    static Gson gson;

    @BeforeClass
    public static void before() {
        gson = new Gson();
        database = new RealDatabase();
    }

    @Test
    public void getGroups() throws UserNotFoundException, ConnectException {
        GroupsArrayJsonObject arrayJsonObject = gson.fromJson(database.getGroups("aaaaxxyyzz"), GroupsArrayJsonObject.class);
        assertNotNull(arrayJsonObject.groupJsonObjects);
    }

    @Test
    public void getGroupFromId() throws GroupNotFoundException, ConnectException {
        GroupJsonObject jsonObject = gson.fromJson(database.getGroupFromId("943b09cc-395e-4c0d-af4c-51f20c2a21d8"), GroupJsonObject.class);
        assertNotNull(jsonObject.date);
        assertNotNull(jsonObject.id);
        assertNotNull(jsonObject.name);
        assertNotNull(jsonObject.debts);
        assertNotNull(jsonObject.members);
    }

    @Test
    public void getUser() throws UserNotFoundException, ConnectException {
        UserJsonObject jsonObject = gson.fromJson(database.getUser("0701234546"), UserJsonObject.class);
        assertNotNull(jsonObject.name);
        assertNotNull(jsonObject.password);
        assertNotNull(jsonObject.phonenumber);
        assertNotNull(jsonObject.contacts);
    }

    @Test
    public void registerUser() throws RegistrationException, ConnectException {
        IDatabase database = new RealDatabase();
        database.registerUser("aaaaxxyyzz", "123", "TestUser1");
        database.registerUser("bbbbxxyyzz", "123", "TestUser2");
        database.registerUser("ccccxxyyzz", "123", "TestUser3");
        database.registerUser("ddddxxyyzz", "123", "TestUser3");
        database.registerUser("eeeexxyyzz", "123", "TestUser3");
    }

    @Test
    public void registerGroup() throws RegistrationException, ConnectException {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("aaaaxxyyzz");
        hashSet.add("bbbbxxyyzz");
        hashSet.add("ccccxxyyzz");
        hashSet.add("ddddxxyyzz");
        hashSet.add("eeeexxyyzz");
        database.registerGroup("TestGroupRegisterAll", hashSet);
        hashSet.remove("ccccxxyyzz");
        hashSet.remove("bbbbxxyyzz");
        database.registerGroup("TestGroupRegisterThree", hashSet);
        hashSet.remove("eeeexxyyzz");
        database.registerGroup("TestGroupRegisterTwo", hashSet);
    }

    @Test
    public void addDebt() throws UserNotFoundException, InvalidDebtException, ConnectException, GroupNotFoundException {
        Set<String> borrowers = new HashSet<>();
        borrowers.add("bbbbxxyyzz");
        borrowers.add("ccccxxyyzz");
        boolean b = database.addDebt("31ed58fc-d82d-4228-b545-305d188387a2", "aaaaxxyyzz", borrowers, new BigDecimal("400"), "no reason");
        assertTrue(b);
    }

    @Test
    public void addContact() throws UserNotFoundException, ConnectException {
        assert (database.addContact("aaaaxxyyzz", "bbbbxxyyzz"));
    }

    @Test
    public void removeContact() throws UserNotFoundException, ConnectException {
        assert(database.removeContact("aaaaxxyyzz", "bbbbxxyyzz"));
    }

    @Test
    public void addPayment() throws InvalidDebtException, InvalidPaymentException, GroupNotFoundException, ConnectException {
        database.addPayment("31ed58fc-d82d-4228-b545-305d188387a2", "2964c77b-ffa6-4064-ac9a-78411c2d97c2", new BigDecimal("200"));
    }

    @Test
    public void addUserToGroup() throws UserNotFoundException, UserAlreadyExistsException, ConnectException, GroupNotFoundException {
        assert(database.addUserToGroup("943b09cc-395e-4c0d-af4c-51f20c2a21d8", "bbbbxxyyzz"));
    }

    @Test
    public void getUserToBeLoggedIn() throws LoginException, ConnectException {
        UserJsonObject jsonObject = gson.fromJson(database.getUserToBeLoggedIn("0701234546", "racso"), UserJsonObject.class);
        assertNotNull(jsonObject.name);
        assertNotNull(jsonObject.password);
        assertNotNull(jsonObject.phonenumber);
        assertNotNull(jsonObject.contacts);
    }

    @Test
    public void getContactList() throws UserNotFoundException {
        ContactsJsonObject jsonObject = gson.fromJson(database.getContactList("0734266227") ,ContactsJsonObject.class);
        assertNotNull(jsonObject.contacts);
    }

    @Test
    public void removeUserFromGroup() throws UserNotFoundException, GroupNotFoundException, ConnectException {
        boolean b = (database.removeUserFromGroup("aaaaxxyyzz", "31ed58fc-d82d-4228-b545-305d188387a2"));
        assert b;
    }

    static class GroupJsonObject {
        public GroupJsonObject(String name, String date, String groupId, UserJsonObject[] members, DebtJsonObject[] debts) {
            this.name = name;
            this.date = date;
            this.id = groupId;
            this.members = members;
            this.debts = debts;
        }

        String name;
        String date;
        String id;
        UserJsonObject[] members;
        DebtJsonObject[] debts;
    }

    static class UserJsonObject {

        public UserJsonObject(String name, String phonenumber, String password, String[] contacts) {
            this.name = name;
            this.phonenumber = phonenumber;
            this.password = password;
            this.contacts = contacts;
        }

        String name;
        String phonenumber;
        String password;
        String[] contacts;
    }

    static class ContactsJsonObject {
        public ContactsJsonObject(UserJsonObject[] contacts) {
            this.contacts = contacts;
        }

        UserJsonObject[] contacts;
    }

    static class GroupsArrayJsonObject {
        GroupJsonObject[] groupJsonObjects;

        public GroupsArrayJsonObject(GroupJsonObject[] groupJsonObjects) {
            this.groupJsonObjects = groupJsonObjects;
        }
    }

    static class DebtJsonObject {
        public DebtJsonObject(UserJsonObject lender, UserJsonObject borrower, String owed, String debtId, PaymentJsonObject[] payments) {
            this.lender = lender;
            this.borrower = borrower;
            this.owed = owed;
            this.id = debtId;
            this.payments = payments;
        }

        UserJsonObject lender;
        UserJsonObject borrower;
        String owed;
        String id;
        PaymentJsonObject[] payments;
    }

    static class PaymentJsonObject {
        public PaymentJsonObject(String amount, String paymentId) {
            this.amount = amount;
            this.id = paymentId;
        }

        String amount;
        String id;
    }
}