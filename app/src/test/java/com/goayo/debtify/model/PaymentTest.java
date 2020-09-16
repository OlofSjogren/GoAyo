package com.goayo.debtify.model;

import com.goayo.debtify.model.debt.Payment;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PaymentTest {

    @Test
    public void testGetDate() {
        Payment p = new Payment(10);
        String paymentDate = p.getDate().toString();
        String testDate = (new Date()).toString();
        assertEquals(paymentDate, testDate);
    }

    @Test
    public void testGetPaidAmount() {
        Payment p = new Payment(25);
        assertEquals(25, p.getPaidAmount(), 0.01);
    }
}