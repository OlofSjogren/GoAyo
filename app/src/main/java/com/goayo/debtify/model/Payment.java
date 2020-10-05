package com.goayo.debtify.model;


import com.goayo.debtify.modelaccess.IPaymentData;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for Payment
 */
class Payment implements IPaymentData {
    private final Date date;
    private final BigDecimal paidAmount;

    public Payment(BigDecimal paidAmount) {
        this.date = new Date();
        this.paidAmount = paidAmount;
    }

    public Date getDate() {
        return (Date)date.clone();
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }
}
