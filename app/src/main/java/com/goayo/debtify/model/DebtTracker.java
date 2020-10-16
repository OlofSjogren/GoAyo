package com.goayo.debtify.model;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * DebtTracker holds debts and keeps track of debt
 * 2020-09-17 Modified by Yenan Wang and Gabriel Brattgård: Updated comments. Added exception on payOffDebt instead of boolean false as return.
 * 2020-09-25 Modified by Olof Sjögren, Alex Phu and Oscar Sanner: Added getRemainingDebt for calculating remaining debt.
 * 2020-09-28 Modified by Yenan Wang: refactor to add description parameter to the constructor
 * 2020-09-29 Modified by Oscar Sanner and Olof Sjögren: Replaced "tempID" with a proper dynamically generated UUID.
 * Also fixed bug in payOffDebt(), now compares larger than AND EQUAL to instead of just larger than.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 */
class DebtTracker implements IDebtData {
    private final Debt debt;
    private final List<Payment> payments;
    private final User lender;
    private final User borrower;
    private final String description;
    private final String debtTrackerID;

    /**
     * Constructor for creating a debt and assigning a lender and borrower.
     *
     * @param debtAmount  how much the borrower owe to lender.
     * @param lender      the user that lends out money.
     * @param borrower    the user that borrows money.
     * @param description the brief description of the debt.
     * @param id          the specific DebtTracker's id.
     */
    public DebtTracker(BigDecimal debtAmount, User lender, User borrower, String description, String id) {
        this.debt = new Debt(debtAmount);
        this.payments = new ArrayList<>();
        this.lender = lender;
        this.borrower = borrower;
        this.debtTrackerID = id;
        this.description = description;
    }

    /**
     * Adds a new payment to the list of payments.
     *
     * @param payOffAmount the amount to pay off.
     */
    public void payOffDebt(BigDecimal payOffAmount) {
        if (debt.getDebtAmount().subtract(getSumOfPayments()).doubleValue() >= payOffAmount.doubleValue()) {
            payments.add(new Payment(payOffAmount));
        } else {
            //TODO: Runtime exception ok?
            throw new RuntimeException("PayOffDebt failed.");
        }
    }

    /**
     * Method for calculating sum of all payments that has been made to this debt.
     *
     * @return the sum of all payments done.
     */
    public BigDecimal getSumOfPayments() {
        BigDecimal sum = new BigDecimal(0);
        for (Payment p : payments) {
            sum = sum.add(p.getPaidAmount());
        }
        return sum;
    }

    /**
     * @return a list of IPaymentData attached to the Debt/DebtTracker.
     */
    @Override
    public List<IPaymentData> getPaymentHistory() {
        return new ArrayList<>(payments);
    }

    /**
     * Calculates remaining debt.
     *
     * @return the remaining amount to be payed.
     */
    @Override
    public BigDecimal getAmountOwed() {
        return debt.getDebtAmount().subtract(getSumOfPayments());
    }

    /**
     * @return the unique ID which is attached to the Debt/DebtTracker
     */
    @Override
    public String getDebtID() {
        return debtTrackerID;
    }

    /**
     * @return the lender of the Debt as a IUserData type.
     */
    @Override
    public IUserData getLender() {
        return lender;
    }

    /**
     * @return the borrower of the Debt as a IUserData type.
     */
    @Override
    public IUserData getBorrower() {
        return borrower;
    }

    /**
     * @return the description of the reasoning behind the Debt.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return the amount of debt specified at the beginning.
     */
    @Override
    public BigDecimal getOriginalDebt() {
        return debt.getDebtAmount();
    }

    /**
     * @return the Date object created along with the instantiation of the Debt.
     */
    @Override
    public Date getDate() {
        return debt.getDate();
    }
}
