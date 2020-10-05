package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for debt
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 */
class Debt {
    private final Date date;
    private final BigDecimal owed;

    public Debt(BigDecimal owed) {
        this.date = new Date();
        this.owed = owed;
    }

    public Date getDate() {
        return (Date)date.clone();
    }

    public BigDecimal getDebtAmount() {
        return owed;
    }
}
