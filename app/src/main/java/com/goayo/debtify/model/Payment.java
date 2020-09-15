package com.goayo.debtify.model;


import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for Payment
 */
public class Payment {
    private final Date date;
    private double paid;

    public Payment(double paid) {
        this.date = new Date("YYYY-MM-DD");
        this.paid = paid;
    }

    public Date getDate() {
        return new Date(date.toString());
    }

    public double getPaid() {
        return paid;
    }
}
