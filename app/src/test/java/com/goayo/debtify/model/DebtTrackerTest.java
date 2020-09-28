package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IPaymentData;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

public class DebtTrackerTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void getSumOfPayments() throws Exception {
        DebtTracker dt = new DebtTracker(350, new User("244","bob"), new User("afa", "afaf"), "abs");
        dt.payOffDebt(50);
        dt.payOffDebt(25);
        dt.payOffDebt(25);
        assertEquals(100, dt.getSumOfPayments(), 0.01);
    }

    @Test
    public void payOffDebt() throws Exception {
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("PayOffDebt failed.");

        DebtTracker dt = new DebtTracker(350, new User("244","bob"), new User("afa", "afaf"), "abs");
        dt.payOffDebt(400);
        dt.payOffDebt(349.9);
        assertEquals(0.1, dt.getAmountOwed(), 0.0001);
    }

    @Test
    public void getPaymentHistory() throws Exception {
        DebtTracker dt = new DebtTracker(350, new User("244","bob"), new User("afa", "afaf"), "abs");
        dt.payOffDebt(35);
        dt.payOffDebt(47);
        List<IPaymentData> listOfPaymentData = dt.getPaymentHistory();
        assertEquals(35, listOfPaymentData.get(0).getPaidAmount(), 0.01);
        assertEquals(47, listOfPaymentData.get(1).getPaidAmount(), 0.01);

    }
}