package com.goayo.debtify.model;

import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for debt
 */
class Debt {
    private final Date date;
    private final double owed;

    public Debt(double owed) {
        this.date = new Date();
        this.owed = owed;
    }

    public Date getDate() {
        return (Date)date.clone();
    }

    public double getDebtAmount() {
        return owed;
    }
}
