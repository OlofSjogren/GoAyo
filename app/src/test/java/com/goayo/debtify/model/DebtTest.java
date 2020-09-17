package com.goayo.debtify.model.debt;

import com.goayo.debtify.model.Debt;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DebtTest {

    @Test
    public void testDebtCreation() {
        Debt debt = new Debt(50);
        assertEquals(50, debt.getDebtAmount(), 0.01);
    }

    @Test
    public void testDebtDate() {
        Debt debt = new Debt(10);
        String debtDate = debt.getDate().toString();
        String testDate = (new Date()).toString();
        assertEquals(debtDate, testDate);
    }
}