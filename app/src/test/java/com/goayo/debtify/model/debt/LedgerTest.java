package com.goayo.debtify.model.debt;

import com.goayo.debtify.model.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LedgerTest {

    @Test
    public void testGetDebtData(){
        Ledger testLedger = new Ledger();
        List<User> testList = new ArrayList<User>();
        testList.add( new User("456", "Bag"));
        assertTrue(testLedger.createDebt(new User("123", "Gab"), testList, 100.0));
        assertEquals("TEMP ID", testLedger.getDebtData("TEMP ID").getDebtID());
    }

    @Test
    public void testCreateDebt() {
        Ledger testLedger = new Ledger();
        List<User> testList = new ArrayList<User>();
        testList.add( new User("456", "Bag"));
        assertTrue(testLedger.createDebt(new User("123", "Gab"), testList, 100.0));
        assertEquals(100, testLedger.getDebtData("TEMP ID").getAmountOwed(), 0.01);
    }

    @Test
    public void testPayOffDebt() {
        Ledger testLedger = new Ledger();
        List<User> testList = new ArrayList<User>();
        testList.add( new User("456", "Bag"));
        assertTrue(testLedger.createDebt(new User("123", "Gab"), testList, 100.0));
        testLedger.payOffDebt(50, "TEMP ID");
        assertEquals(50,testLedger.getDebtData("TEMP ID").getAmountOwed(), 0.01);
    }
}