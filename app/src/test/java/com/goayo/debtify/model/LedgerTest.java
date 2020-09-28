package com.goayo.debtify.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class LedgerTest {

    @Test
    public void testGetDebtData() throws Exception {
        Ledger testLedger = new Ledger();
        Set<User> testUserSet = new HashSet<User>();
        testUserSet.add( new User("456", "Bag"));
        testLedger.createDebt(new User("123", "Gab"), testUserSet, 100.0, "abs");
        assertEquals("TEMP ID", testLedger.getDebtData("TEMP ID").getDebtID());
    }

    @Test
    public void testCreateDebt() throws Exception {
        Ledger testLedger = new Ledger();
        Set<User> testUserSet = new HashSet<User>();
        testUserSet.add( new User("456", "Bag"));
        testLedger.createDebt(new User("123", "Gab"), testUserSet, 100.0, "abs");
        assertEquals(100, testLedger.getDebtData("TEMP ID").getAmountOwed(), 0.01);
    }

    @Test
    public void testPayOffDebt() throws Exception {
        Ledger testLedger = new Ledger();
        Set<User> testUserSet = new HashSet<User>();
        testUserSet.add( new User("456", "Bag"));
        testLedger.createDebt(new User("123", "Gab"), testUserSet, 100.0, "abs");
        testLedger.payOffDebt(50, "TEMP ID");
        assertEquals(50,testLedger.getDebtData("TEMP ID").getAmountOwed(), 0.01);
    }

    @Test
    public void testGetUserTotal(){
        //TODO Write test.
    }
}