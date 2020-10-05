package com.goayo.debtify.modelaccess;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Interface for accessing debt data
 * <p>
 * 2020-09-16 Modified by Gabriel & Yenan : Added getOriginalDebt() and getAmountOwed() to account for payments done.
 * 2020-09-28 Modified by Yenan: add getDebtDescription() method and add comments
 */
public interface IDebtData {
    /**
     * @return the unique ID which is attached to the Debt/DebtTracker
     */
    String getDebtID();

    /**
     * @return the lender of the Debt
     */
    IUserData getLender();

    /**
     * @return the borrower of the Debt
     */
    IUserData getBorrower();

    /**
     * @return a short, brief description of the reasoning behind the Debt
     */
    String getDescription();

    /**
     * @return the amount of debt specified at the beginning
     */
    BigDecimal getOriginalDebt();

    /**
     * @return the amount of debt left now
     */
    BigDecimal getAmountOwed();

    /**
     * @return a list of IPaymentData attached to the Debt/DebtTracker
     */
    List<IPaymentData> getPaymentHistory();

    /**
     * @return the Date object created along with the instantiation of the Debt
     */
    Date getDate();
}
