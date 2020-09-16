package com.goayo.debtify.model.debt;


import com.goayo.debtify.modelaccess.IPaymentData;

import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for Payment
 */
public class Payment implements IPaymentData {
    private final Date date;
    private final double paidAmount;

    public Payment(double paidAmount) {
        this.date = new Date();
        this.paidAmount = paidAmount;
    }

    public Date getDate() {
        return (Date)date.clone();
    }

    public double getPaidAmount() {
        return paidAmount;
    }
}
