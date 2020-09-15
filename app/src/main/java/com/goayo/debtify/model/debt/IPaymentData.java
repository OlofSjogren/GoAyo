package com.goayo.debtify.model.debt;

import java.util.Date;


/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Interface for accessing payment data
 */
public interface IPaymentData {
    double getPaidAmount();
    Date getDate();
}
