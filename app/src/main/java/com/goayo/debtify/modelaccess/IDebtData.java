package com.goayo.debtify.modelaccess;

import java.util.Date;
import java.util.List;


/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Interface for accessing debt data
 *
 * 2020-09-16 Modified by Gabriel & Yenan : Added getOriginalDebt() and getAmountOwed() to account for payments done.
 */
public interface IDebtData {
    String getDebtID();
    IUserData getLender();
    IUserData getBorrower();
    double getOriginalDebt();
    double getAmountOwed();
    List<IPaymentData> getPaymentHistory();
    Date getDate();
}
