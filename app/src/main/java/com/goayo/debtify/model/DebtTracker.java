package com.goayo.debtify.model;


import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IPaymentData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * DebtTracker holds debts and keeps track of debt
 * 2020-09-17 Modified by Yenan & Gabriel : Updated comments. Added exception on payOffDebt instead of boolean false as return.
 * 2020-09-25 Modified by Olof Sjögren, Alex Phu & Oscar Sanner : Added getRemainingDebt for calculating remaining debt.
 */
class DebtTracker implements IDebtData {
    private final Debt debt;
    private final List<Payment> payments;
    private final User lender;
    private final User borrower;
    private final String debtTrackerID;

    /**
     * constructor creates a debt and assigns lender and borrower
     *
     * @param debtAmount how much the borrower owe to lender
     * @param lender     the user that lends out money
     * @param borrower   the user that borrows money
     */
    public DebtTracker(double debtAmount, User lender, User borrower) {
        this.debt = new Debt(debtAmount);
        this.payments = new ArrayList<>();
        this.lender = lender;
        this.borrower = borrower;
        // TODO: need a util class to generate IDs
        this.debtTrackerID = "TEMP ID";
    }

    /**
     * constructor for creating a clone of this DebtTracker for defensive copying
     *
     * @param debtTracker the debtTracker to clone
     */
    public DebtTracker(DebtTracker debtTracker) {
        this.debt = debtTracker.debt;
        this.payments = new ArrayList<>(debtTracker.payments);
        this.lender = debtTracker.lender;
        this.borrower = debtTracker.borrower;
        this.debtTrackerID = debtTracker.debtTrackerID;
    }

    /**
     * Adds a new payment to the list of payments
     *
     * @param payOffAmount the amount to pay off
     * @throws if payOffAmount exceeds debt left to pay
     */
    public void payOffDebt(double payOffAmount) throws Exception {
        if (debt.getDebtAmount() - getSumOfPayments() > payOffAmount) {
            payments.add(new Payment(payOffAmount));
        } else {
            //TODO: Sepcify what exception this is.
            throw new Exception("PayOffDebt failed.");
        }
    }

    /**
     * @return sum of all payments done
     */
    public double getSumOfPayments() {
        double sum = 0;
        for (Payment p : payments) {
            sum += p.getPaidAmount();
        }
        return sum;
    }

    /**
     * Calculates remaining debt.
     *
     * @return the remaining amount to be payed.
     */
    public double getRemainingDebt() {
        return debt.getDebtAmount() - getSumOfPayments();
    }

    /**
     * @return a list of all payment history
     */
    @Override
    public List<IPaymentData> getPaymentHistory() {
        return new ArrayList<IPaymentData>(payments);
    }

    @Override
    public double getAmountOwed() {
        return debt.getDebtAmount() - getSumOfPayments();
    }

    @Override
    public String getDebtID() {
        return debtTrackerID;
    }

    @Override
    public IUserData getLender() {
        return lender;
    }

    @Override
    public IUserData getBorrower() {
        return borrower;
    }

    @Override
    public double getOriginalDebt() {
        return debt.getDebtAmount();
    }

    @Override
    public Date getDate() {
        return debt.getDate();
    }
}
