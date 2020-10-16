package com.goayo.debtify.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class LedgerTest {



    final String pnOne = "0101010101";
    final String pnTwo = "1010101010";
    Ledger ledger;
    User user;
    User contact;


    @Before
    public void beforeClass() throws Exception {

        user = new User(pnOne, "testOne");
        contact = new User(pnTwo, "testTwo");

        Map<User, String> members = new HashMap<>();
        members.put(contact, "TestID");

        ledger = new Ledger();
        ledger.createDebt(user, members, new BigDecimal(100), "Test", new EvenSplitStrategy(), new Date());
    }

    @Test
    public void testGetDebtData() throws Exception {
        IDebtData debt = ledger.getDebtData("TestID");
        assertEquals("Test", debt.getDescription());
        assertEquals(100, debt.getAmountOwed().intValue());
        assertEquals("TestID", debt.getDebtID());
        assertEquals(100, debt.getOriginalDebt().intValue());
        assertEquals(debt.getLender(), user);
        assertEquals(debt.getBorrower().getPhoneNumber(), contact.getPhoneNumber());
    }

    @Test
    public void testPayOffDebt() throws Exception {
        ledger.payOffDebt(new BigDecimal(50), "TestID", new Date());
    }

    @Test
    public void testGetUserTotal() throws UserNotFoundException {
        ledger.getUserTotal(user);
    }
}