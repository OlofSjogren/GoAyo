package com.goayo.debtify.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class LedgerTest {


    final String pnOne = "0101010101";
    final String pnTwo = "1010101010";
    Ledger ledger;
    User user;
    User contact;


    @Before
    public void beforeClass() throws DebtException {

        user = new User(pnOne, "testOne");
        contact = new User(pnTwo, "testTwo");

        Map<User, String> members = new HashMap<>();
        members.put(contact, "TestID");

        ledger = new Ledger();
        ledger.createDebt(user, members, new BigDecimal(100), "Test", new EvenSplitStrategy(), new Date());
    }

    @Test
    public void testGetDebtData() throws InvalidPaymentException {
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

    @Test
    public void debtCreationTest() {
        Ledger testLedger = new Ledger();
        User u1 = new User("070", "John");
        User u2 = new User("071", "Lisa");

        Map<User, String> emptyBorrowers = new HashMap<>();
        Map<User, String> borrowers = new HashMap<>();
        borrowers.put(u2, "TESTID");

        //Assert exception when lender is null.
        Assert.assertThrows(DebtException.class,
                () -> testLedger.createDebt(null, borrowers, new BigDecimal("1"), "des", DebtSplitFactory.createEvenSplitStrategy(), new Date())
        );
        //Assert exception when borrowers is empty.
        Assert.assertThrows(DebtException.class,
                () -> testLedger.createDebt(u1, emptyBorrowers, new BigDecimal("1"), "des", DebtSplitFactory.createEvenSplitStrategy(), new Date())
        );

        emptyBorrowers.put(null, "ID---");

        //Assert exception when emptyBorrowers list entries are incorrect.
        Assert.assertThrows(DebtException.class,
                () -> testLedger.createDebt(u1, emptyBorrowers, new BigDecimal("1"), "des", DebtSplitFactory.createEvenSplitStrategy(), new Date())
        );

        //Assert exception when debt amount is zero.
        Assert.assertThrows(DebtException.class,
                () -> testLedger.createDebt(u1, borrowers, new BigDecimal("0"), "des", DebtSplitFactory.createEvenSplitStrategy(), new Date())
        );
        //Assert exception when debt amount is negative.
        Assert.assertThrows(DebtException.class,
                () -> testLedger.createDebt(u1, borrowers, new BigDecimal("-1"), "des", DebtSplitFactory.createEvenSplitStrategy(), new Date())
        );
        //Assert exception when date is null.
        Assert.assertThrows(DebtException.class,
                () -> testLedger.createDebt(u1, borrowers, new BigDecimal("1"), "des", DebtSplitFactory.createEvenSplitStrategy(), null)
        );



    }
}