package com.goayo.debtify.model;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Value object for Payment
 * <p>
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-13 Modified by Olof Sjögren: getPaidAmount now returns a new BigDecimal. Also added more JDocs.
 * 2020-10-15 Modified by Yenan Wang & Alex Phu: implemented compareTo(..) method
 */
class Payment implements IPaymentData {
    private final Date date;
    private final BigDecimal paidAmount;

    public Payment(BigDecimal paidAmount) {
        this.date = new Date();
        this.paidAmount = paidAmount;
    }

    /**
     * Method for retrieving payment date.
     *
     * @return clone of the date. Used to avoid unexpected mutations.
     */
    public Date getDate() {
        return (Date) date.clone();
    }

    /**
     * Method for retrieving the payment's amount.
     *
     * @return a new BigDecimal with the same amount as the paidAmount. Used to avoid unexpected mutations.
     */
    public BigDecimal getPaidAmount() {
        return new BigDecimal(paidAmount.toString());
    }

    @Override
    public int compareTo(IPaymentData payment) {
        return date.compareTo(payment.getDate());
    }
}
