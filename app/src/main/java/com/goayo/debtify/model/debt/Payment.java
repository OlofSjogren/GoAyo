package com.goayo.debtify.model.debt;


import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for Payment
 */
public class Payment implements IPaymentData{
    private final Date date;
    private final double paid;

    public Payment(double paid) {
        this.date = new Date();
        this.paid = paid;
    }

    public Date getDate() {
        return (Date)date.clone();
    }

    public double getPaidAmount() {
        return paid;
    }
}
