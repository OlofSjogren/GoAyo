package com.goayo.debtify.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class DebtTrackerTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void getSumOfPayments() throws Exception {
        DebtTracker dt = new DebtTracker(new BigDecimal(350), new User("244","bob"), new User("1111001100", "afaf"), "abs", "TestID");
        dt.payOffDebt(new BigDecimal(50));
        dt.payOffDebt(new BigDecimal(25));
        dt.payOffDebt(new BigDecimal(25));
        assertEquals(new BigDecimal(100), dt.getSumOfPayments());
    }

    @Test
    public void payOffDebt() throws Exception {
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("PayOffDebt failed.");

        DebtTracker dt = new DebtTracker(new BigDecimal(350), new User("244","bob"), new User("1111001100", "afaf"), "abs", "TestID");
        dt.payOffDebt(new BigDecimal(400));
        dt.payOffDebt(new BigDecimal("349.9"));
        assertEquals(new BigDecimal("0.01"), dt.getAmountOwed());
    }

    @Test
    public void getPaymentHistory() throws Exception {
        DebtTracker dt = new DebtTracker(new BigDecimal(350), new User("244","bob"), new User("1111001100", "afaf"), "abs", "TestID");
        dt.payOffDebt(new BigDecimal(35));
        dt.payOffDebt(new BigDecimal(47));
        List<IPaymentData> listOfPaymentData = dt.getPaymentHistory();
        assertEquals(new BigDecimal(35), listOfPaymentData.get(0).getPaidAmount());
        assertEquals(new BigDecimal(47), listOfPaymentData.get(1).getPaidAmount());

    }
}