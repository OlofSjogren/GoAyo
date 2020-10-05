package com.goayo.debtify.modelaccess;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Interface for accessing payment data
 */
public interface IPaymentData {
    BigDecimal getPaidAmount();
    Date getDate();
}
