package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IPaymentData;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test class written for the mock database. This database was written to test the specification
 * documented in this repository for the mock database.
 *
 *
 */

public class MockDatabaseTest {

    private IDatabase database;

    @Before
    public void setUp() throws Exception {
        database = new MockDatabase();
    }

    @Test
    public void getGroups() {
        Set<Group> groupSet = database.getGroups("0786458765");
        assert (groupSet.contains(database.getGroupFromId("4116c93e-5542-4b5c-8423-010a901abdce")));
        assert (groupSet.contains(database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a")));
        assert (groupSet.size() == 2);
    }

    @Test
    public void getGroupFromId() {
        assert (database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a").getGroupMembers().size() == 5);
        assert (database.getGroupFromId("4116c93e-5542-4b5c-8423-010a901abdce").getGroupMembers().size() == 3);
        assert (database.getGroupFromId("d467b5bc-5fa9-4ac2-890d-29a07803d484").getGroupMembers().size() == 3);
    }

    @Test
    public void getUser() {
        assert (database.getUser("0738980732").getName().equals("Alex Phu"));
    }

    @Test
    public void registerUser() {
        String testPhoneNumber = "0987123454";
        database.registerUser(testPhoneNumber, "flar", "Ralf Broberg");
        assert (database.getUser(testPhoneNumber).getName().equals("Ralf Broberg"));
    }

    @Test
    public void registerGroup() {
        Set<String> userToBeAdded = new HashSet<>();
        int oscarSizeBefore = database.getGroups("0701234546").size();
        int alexSizeBefore = database.getGroups("0738980732").size();
        int gabrielSizeBefore = database.getGroups("0733387676").size();


        userToBeAdded.add("0701234546");
        userToBeAdded.add("0738980732");
        userToBeAdded.add("0733387676");
        String groupName = "Testgroup";

        database.registerGroup(groupName, userToBeAdded);

        int oscarSizeAfter = database.getGroups("0701234546").size();
        int alexSizeAfter = database.getGroups("0738980732").size();
        int gabrielSizeAfter = database.getGroups("0733387676").size();

        assert (gabrielSizeAfter - gabrielSizeBefore == 1);
        assert (alexSizeAfter - alexSizeBefore == 1);
        assert (oscarSizeAfter - oscarSizeBefore == 1);
    }

    @Test
    public void addDebt() throws Exception {

        Set<String> alexBorrowerSet = new HashSet<>();
        alexBorrowerSet.add("0738980732");

        List<IDebtData> debtDataBefore = database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a").getDebts();

        try {
            database.addDebt("1a705586-238d-4a29-b7af-36dc103bd45a", "0701234546", alexBorrowerSet, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<IDebtData> debtDataAfter = database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a").getDebts();
        for(IDebtData debtData : debtDataBefore){
            if(debtDataAfter.contains(debtData)){
                debtDataAfter.remove(debtData);
            }
        }

        assert (debtDataAfter.size() == 1);
        assert (debtDataAfter.get(0).getAmountOwed() == 20);
        assert (debtDataAfter.get(0).getBorrower().getName().equals("Alex Phu"));
        assert (debtDataAfter.get(0).getLender().getName().equals("Oscar Sanner"));

        assert (!(database.addDebt("Non existent Id", "Non existent number", new HashSet<String>(), 20)));
        assert (!(database.addDebt("1a705586-238d-4a29-b7af-36dc103bd45a", "Non existent number", new HashSet<String>(), 20)));
        assert (!(database.addDebt("1a705586-238d-4a29-b7af-36dc103bd45a", "0701234546", new HashSet<String>(), 20)));

    }

    @Test
    public void addContact() {
        User alex = database.getUser("0738980732");
        User oscar = database.getUser("0701234546");
        assert (!(database.getContactList(alex.getPhoneNumber()).contains(oscar)));
        database.addContact(alex.getPhoneNumber(), oscar.getPhoneNumber());
        assert (database.getContactList(alex.getPhoneNumber()).contains(oscar));
    }

    @Test
    public void removeContact() {
        User alex = database.getUser("0738980732");
        User oscar = database.getUser("0701234546");

        assert (database.getContactList(oscar.getPhoneNumber()).contains(alex));
        database.removeContact(oscar.getPhoneNumber(), alex.getPhoneNumber());
        assert (!(database.getContactList(oscar.getPhoneNumber()).contains(alex)));
    }

    @Test
    public void addPayment() {
        Set<String> alexBorrowerSet = new HashSet<>();
        alexBorrowerSet.add("0738980732");

        List<IDebtData> debtDataBefore = database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a").getDebts();

        try {
            database.addDebt("1a705586-238d-4a29-b7af-36dc103bd45a", "0701234546", alexBorrowerSet, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<IDebtData> debtDataAfter = database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a").getDebts();
        for(IDebtData debtData : debtDataBefore){
            if(debtDataAfter.contains(debtData)){
                debtDataAfter.remove(debtData);
            }
        }

        List<IPaymentData> paymentsBefore = debtDataAfter.get(0).getPaymentHistory();
        try {
            database.addPayment("1a705586-238d-4a29-b7af-36dc103bd45a", debtDataAfter.get(0).getDebtID(), 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<IPaymentData> paymentsAfter = debtDataAfter.get(0).getPaymentHistory();

        assert (paymentsBefore.size() == 0);
        assert (paymentsAfter.size() == 1);
        assert (paymentsAfter.get(0).getPaidAmount() == 15);
        assert (debtDataAfter.get(0).getAmountOwed() == 5);
        assert (debtDataAfter.get(0).getOriginalDebt() - paymentsAfter.get(0).getPaidAmount() == debtDataAfter.get(0).getAmountOwed());

    }

    @Test
    public void addUserToGroup() {
        User oscar = database.getUser("0701234546");
        assert (!(database.getGroupFromId("d467b5bc-5fa9-4ac2-890d-29a07803d484").getGroupMembers().contains(oscar)));
        database.addUserToGroup("d467b5bc-5fa9-4ac2-890d-29a07803d484", oscar.getPhoneNumber());
        assert (database.getGroupFromId("d467b5bc-5fa9-4ac2-890d-29a07803d484").getGroupMembers().contains(oscar));
    }

    @Test
    public void getUserToBeLoggedIn() {
        User oscar = database.getUserToBeLoggedIn("0701234546", "racso");
        assertNotNull(oscar);
        User noUser = database.getUserToBeLoggedIn("0987467281", "123");
        assertNull(noUser);
        User wrongPw = database.getUserToBeLoggedIn("0701234546", "123");
        assertNull(wrongPw);
        User wrongNumber = database.getUserToBeLoggedIn("0987467281", "racso");
        assertNull(wrongNumber);
    }

    @Test
    public void removeUserFromGroup() {
        User removedUser1 = database.getUser("0701234546");
        User removedUser2 = database.getUser("0786458765");
        User removedUser3 = database.getUser("0738980732");
        User removedUser4 = database.getUser("0701094578");
        User remainingUser5 = database.getUser("0733387676");

        Set<User> removedUserSet = new HashSet<>();
        removedUserSet.add(removedUser2);
        removedUserSet.add(removedUser3);
        removedUserSet.add(removedUser4);

        Group testGroup = database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a");

        testGroup.removeUser(removedUser1);
        testGroup.removeUser(removedUserSet);

        assertFalse(testGroup.getGroupMembers().contains(removedUser1));
        for (User u : removedUserSet){
            assertFalse(testGroup.getGroupMembers().contains(u));
        }
        assertTrue(testGroup.getGroupMembers().contains(remainingUser5));
        assertTrue(testGroup.getGroupMembers().size() == 1);

    }

    @Test
    public void getContactList() {
        assertTrue(database.getContactList("0701234546").contains(database.getUser("0738980732")));
        assertTrue(database.getContactList("0701234546").contains(database.getUser("0733387676")));

        assertTrue(database.getContactList("0786458765").contains(database.getUser("0701094578")));
        assertTrue(database.getContactList("0786458765").contains(database.getUser("0701234546")));

        assertTrue(database.getContactList("0738980732").contains(database.getUser("0786458765")));
        assertTrue(database.getContactList("0738980732").contains(database.getUser("0733387676")));

        assertTrue(database.getContactList("0701094578").contains(database.getUser("0701234546")));
        assertTrue(database.getContactList("0701094578").contains(database.getUser("0786458765")));
        assertTrue(database.getContactList("0701094578").contains(database.getUser("0738980732")));
        assertTrue(database.getContactList("0701094578").contains(database.getUser("0733387676")));

        assertTrue(database.getContactList("0733387676").isEmpty());


    }
}