package com.goayo.debtify.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class PaymentTest {

    @Test
    public void testGetDate() {
        Payment p = new Payment(new BigDecimal(10), new Date());
        String paymentDate = p.getDate().toString();
        String testDate = (new Date()).toString();
        assertEquals(paymentDate, testDate);
    }

    @Test
    public void testGetPaidAmount() {
        Payment p = new Payment(new BigDecimal(25), new Date());
        assertEquals(new BigDecimal("25.00"), p.getPaidAmount());
    }
}