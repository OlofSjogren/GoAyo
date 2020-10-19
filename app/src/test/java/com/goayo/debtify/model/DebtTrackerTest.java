package com.goayo.debtify.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DebtTrackerTest {

    @Test
    public void getSumOfPayments() {
        DebtTracker dt = new DebtTracker(new BigDecimal(500), new User("244","bob")
                , new User("1111001100", "afaf")
                , "abs", "TestID", new Date());

        //Randomize payment size
        Random rnd = new Random(System.nanoTime());
        BigDecimal firstPayment = new BigDecimal(rnd.nextInt(60-20+1)+20);  //Bounds [20, 60]
        BigDecimal secondPayment = new BigDecimal(rnd.nextInt(80-30)+30); //Bounds [30, 80)

        dt.payOffDebt(firstPayment, new Date());
        dt.payOffDebt(secondPayment, new Date());

        //Actual total sum of payments
        BigDecimal totalPayments = new BigDecimal(0).add(firstPayment).add(secondPayment);

        assertEquals(totalPayments.setScale(2, BigDecimal.ROUND_HALF_EVEN), dt.getSumOfPayments());
    }

    @Test
    public void payOffDebt() {
        Random rnd = new Random(System.nanoTime());

        BigDecimal totalDebt = new BigDecimal(rnd.nextInt(600-400)+400); //Bounds [400, 600)

        DebtTracker dt = new DebtTracker(totalDebt
                , new User("244","bob")
                , new User("1111001100", "afaf")
                , "abs", "TestID", new Date());

        BigDecimal firstPayment = new BigDecimal(rnd.nextInt(35-20)+20); //Bounds [20, 35)
        BigDecimal secondPayment = new BigDecimal(rnd.nextInt(253-100+1)+100); //Bounds [100, 253]

        dt.payOffDebt(firstPayment, new Date());
        dt.payOffDebt(secondPayment, new Date());

        //Actual current state of total debt
        totalDebt = totalDebt.subtract(firstPayment).subtract(secondPayment);
        assertEquals(totalDebt.setScale(2, RoundingMode.HALF_EVEN), dt.getAmountOwed());
    }

    @Test
    public void getPaymentHistory() {
        Random rnd = new Random(System.nanoTime());
        BigDecimal totalDebt = new BigDecimal(rnd.nextInt(600-400)+400); //Bounds [400, 600)

        DebtTracker dt = new DebtTracker(totalDebt
                , new User("244","bob")
                , new User("1111001100", "afaf")
                , "abs", "TestID", new Date());

        BigDecimal firstPayment = new BigDecimal(rnd.nextInt(35-20)+20); //Bounds [20, 35)
        BigDecimal secondPayment = new BigDecimal(rnd.nextInt(253-100+1)+100); //Bounds [100, 253]
        dt.payOffDebt(firstPayment, new Date());
        dt.payOffDebt(secondPayment, new Date());

        List<IPaymentData> listOfPaymentData = dt.getPaymentHistory();
        assertEquals(firstPayment.setScale(2, RoundingMode.HALF_EVEN), listOfPaymentData.get(0).getPaidAmount());
        assertEquals(secondPayment.setScale(2, RoundingMode.HALF_EVEN), listOfPaymentData.get(1).getPaidAmount());

    }
}