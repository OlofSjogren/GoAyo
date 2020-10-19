package com.goayo.debtify.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

public class DebtTest {

    private final Random rnd = new Random(System.nanoTime());

    @Test
    public void testDebtCreation() {
        int random_int = rnd.nextInt();
        Debt debt = new Debt(new BigDecimal(random_int), new Date());
        assertEquals(0, debt.getDebtAmount().compareTo(new BigDecimal(random_int)));
    }

    @Test
    public void testDebtDate() {
        Debt debt = new Debt(new BigDecimal(10), new Date());
        String debtDate = debt.getDate().toString();
        String testDate = (new Date()).toString();
        assertEquals(debtDate, testDate);
    }
}