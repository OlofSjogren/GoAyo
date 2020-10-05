package com.goayo.debtify.modelaccess;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Interface for accessing payment data
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 */
public interface IPaymentData {
    BigDecimal getPaidAmount();
    Date getDate();
}
