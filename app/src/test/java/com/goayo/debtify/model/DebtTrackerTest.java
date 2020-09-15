package com.goayo.debtify.model;

import android.util.Pair;

import com.goayo.debtify.model.debt.DebtTracker;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DebtTrackerTest {

    @Test
    public void getSumOfPayments() {
        DebtTracker dt = new DebtTracker(350, new User("244","bob"), new User("afa", "afaf"));
        dt.payOffDebt(50);
        dt.payOffDebt(25);
        dt.payOffDebt(25);
        assertEquals(100, dt.getSumOfPayments(), 0.01);
    }

    @Test
    public void payOffDebt() {
        DebtTracker dt = new DebtTracker(350, new User("244","bob"), new User("afa", "afaf"));
        assertFalse(dt.payOffDebt(400));
        assertTrue(dt.payOffDebt(349.9));
    }

    @Test
    public void getPaymentHistory() {
        DebtTracker dt = new DebtTracker(350, new User("244","bob"), new User("afa", "afaf"));
        dt.payOffDebt(35);
        dt.payOffDebt(47);
        List<Pair<Date, Double>> listOfPair = dt.getPaymentHistory();
        assertEquals(35, listOfPair.get(0).second, 0.01);
        assertEquals(47, listOfPair.get(1).second, 0.01);

    }
}