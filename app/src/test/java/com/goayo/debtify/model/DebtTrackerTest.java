package com.goayo.debtify.model.debt;

import com.goayo.debtify.model.User;
import com.goayo.debtify.model.DebtTracker;
import com.goayo.debtify.modelaccess.IPaymentData;

import org.junit.Test;

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
        List<IPaymentData> listOfPaymentData = dt.getPaymentHistory();
        assertEquals(35, listOfPaymentData.get(0).getPaidAmount(), 0.01);
        assertEquals(47, listOfPaymentData.get(1).getPaidAmount(), 0.01);

    }
}