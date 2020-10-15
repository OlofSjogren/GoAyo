package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for debt
 * <p>
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-14 Modified by Olof Sjögren: getDebtAmount now returns a new BigDecimal, also updated JDocs.
 */
class Debt {
    private final Date date;
    private final BigDecimal owed;

    public Debt(BigDecimal owed) {
        this.date = new Date();
        this.owed = owed;
    }

    /**
     * Method for retrieving debt date.
     *
     * @return clone of the date. Used to avoid unexpected mutations.
     */
    public Date getDate() {
        return (Date) date.clone();
    }

    /**
     * Method for retrieving the debt's amount.
     *
     * @return a new BigDecimal with the same amount as the owed amount. Used to avoid unexpected mutations.
     */
    public BigDecimal getDebtAmount() {
        return new BigDecimal(owed.toString());
    }
}
