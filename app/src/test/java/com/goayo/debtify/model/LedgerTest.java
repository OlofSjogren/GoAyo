package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IPaymentData;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class LedgerTest {


    private IDatabase database;
    private Account account;
    private Group group;

    @Before
    public void beforeClass() throws Exception {
        database = new MockDatabase();
        account = new Account(database);
        group = database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a");
        account.loginUser("0701234546", "racso");
    }

    @Test
    public void testGetDebtData() throws Exception {
        assertEquals(5, group.getDebts().size());
    }

    @Test
    public void testCreateDebt() throws Exception {
        Set<String> alexBorrowerSet = new HashSet<>();
        alexBorrowerSet.add("0738980732");

        List<IDebtData> debtDataBefore = database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a").getDebts();

        try {
            database.addDebt("1a705586-238d-4a29-b7af-36dc103bd45a", "0701234546", alexBorrowerSet, 20, "TestDebt0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<IDebtData> debtDataAfter = database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a").getDebts();
        for(IDebtData debtData : debtDataBefore){
            if(debtDataAfter.contains(debtData)){
                debtDataAfter.remove(debtData);
            }
        }

        assertEquals(1, debtDataAfter.size());
        assertEquals(20, debtDataAfter.get(0).getAmountOwed(), 0.0);
        assertEquals("Alex Phu", debtDataAfter.get(0).getBorrower().getName());
        assertEquals("Oscar Sanner", debtDataAfter.get(0).getLender().getName());

        assertFalse(database.addDebt("Non existent Id", "Non existent number", new HashSet<String>(), 20, "TestDebt1"));
        assertFalse(database.addDebt("1a705586-238d-4a29-b7af-36dc103bd45a", "Non existent number", new HashSet<String>(), 20, "TestDebt2"));
        assertFalse(database.addDebt("1a705586-238d-4a29-b7af-36dc103bd45a", "0701234546", new HashSet<String>(), 20, "TestDebt3"));

    }

    @Test
    public void testPayOffDebt() throws Exception {
        Set<String> alexBorrowerSet = new HashSet<>();
        alexBorrowerSet.add("0738980732");

        List<IDebtData> debtDataBefore = database.getGroupFromId("1a705586-238d-4a29-b7af-36dc103bd45a").getDebts();

        try {
            database.addDebt("1a705586-238d-4a29-b7af-36dc103bd45a", "0701234546", alexBorrowerSet, 20, "Test Description");
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

        assertEquals(0, paymentsBefore.size());
        assertEquals(1, paymentsAfter.size());
        assertEquals(15, paymentsAfter.get(0).getPaidAmount(), 0.0);
        assertEquals(5, debtDataAfter.get(0).getAmountOwed(), 0.0);
        assertEquals(debtDataAfter.get(0).getOriginalDebt() - paymentsAfter.get(0).getPaidAmount(), debtDataAfter.get(0).getAmountOwed(), 0.0);
    }

    @Test
    public void testGetUserTotal() throws UserNotFoundException {
        double total =  group.getUserTotal("0701234546");
        assertEquals(-65.25, total, 0.0);
    }
}