package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Interface for accessing debt data
 * <p>
 * 2020-09-16 Modified by Gabriel Brattgård & Yenan Wang: Added getOriginalDebt() and getAmountOwed() to account for payments done.
 * 2020-09-28 Modified by Yenan Wang: add getDebtDescription() method and add comments
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 */
public interface IDebtData {
    /**
     * @return the unique ID.
     */
    String getDebtID();

    /**
     * @return the lender of the Debt as a IUserData type.
     */
    IUserData getLender();

    /**
     * @return the borrower of the Debt as a IUserData type.
     */
    IUserData getBorrower();

    /**
     * @return the description of the reasoning behind the Debt.
     */
    String getDescription();

    /**
     * @return the amount of debt specified at the beginning.
     */
    BigDecimal getOriginalDebt();

    /**
     * @return the amount of debt left.
     */
    BigDecimal getAmountOwed();

    /**
     * @return a list of IPaymentData attached to the Debt/DebtTracker.
     */
    List<IPaymentData> getPaymentHistory();

    /**
     * @return the Date object created along with the instantiation of the Debt.
     */
    Date getDate();
}
