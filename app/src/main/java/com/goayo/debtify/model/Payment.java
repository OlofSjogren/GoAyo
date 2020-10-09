package com.goayo.debtify.model;


import com.goayo.debtify.modelaccess.IPaymentData;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for Payment
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 */
class Payment implements IPaymentData {
    private final Date date;
    private final BigDecimal paidAmount;
    String id;

    public Payment(BigDecimal paidAmount) {
        this.date = new Date();
        this.paidAmount = paidAmount;
        this.id = id;
    }

    public Date getDate() {
        return (Date)date.clone();
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }
}
