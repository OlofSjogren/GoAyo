package com.goayo.debtify.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class DebtTest {

    @Test
    public void testDebtCreation() {
        Debt debt = new Debt(new BigDecimal(50), new Date());
        assertEquals(0, debt.getDebtAmount().compareTo(new BigDecimal(50)));
    }

    @Test
    public void testDebtDate() {
        Debt debt = new Debt(new BigDecimal(10), new Date());
        String debtDate = debt.getDate().toString();
        String testDate = (new Date()).toString();
        assertEquals(debtDate, testDate);
    }
}