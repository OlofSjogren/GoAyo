package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Interface for accessing payment data
 * Meant primarily to be used as a wrapper for model objects fetched from outside the model.
 * <p>
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 * 2020-10-15 Modified by Yenan Wang & Alex Phu: interface now extends Comparable
 */
public interface IPaymentData extends Comparable<IPaymentData> {

    /**
     * @return the amount that is paid of the debt total.
     */
    BigDecimal getPaidAmount();

    /**
     * @return the date the debt was added.
     */
    Date getDate();
}
