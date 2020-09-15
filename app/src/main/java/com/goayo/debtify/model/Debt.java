package com.goayo.debtify.model;

import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for debt
 */
public class Debt {
    private final Date date;
    private double owed;

    public Debt(double owed) {
        this.date = new Date("YYYY-MM-DD");
        this.owed = owed;
    }

    public Date getDate() {
        return new Date(date.toString());
    }

    public double getOwed() {
        return owed;
    }
}
