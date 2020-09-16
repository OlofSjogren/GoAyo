package com.goayo.debtify.model.debt;


import com.goayo.debtify.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * DebtTracker holds debts and keeps track of debt
 */
public class DebtTracker implements IDebtData {
    private final Debt debt;
    private final List<Payment> payments;
    private final User lender;
    private final User borrower;
    private final String debtTrackerID;

    /**
     * constructor creates a debt and assigns lender and borrower
     *
     * @param debtAmount how much the borrower owe to lender
     * @param lender the user that lends out money
     * @param borrower the user that borrows money
     */
    public DebtTracker(double debtAmount, User lender, User borrower) {
        this.debt = new Debt(debtAmount);
        this.payments = new ArrayList<>();
        this.lender = lender;
        this.borrower = borrower;
        // TODO: need a util class to generate IDs
        this.debtTrackerID = "TEMP ID";
    }

    public double getSumOfPayments() {
        double sum = 0;
        for (Payment p : payments) {
            sum += p.getPaidAmount();
        }
        return sum;
    }

    /**
     * Adds a new payment to the list of payments
     *
     * @param payOffAmount the amount to pay off
     * @return false if payOffAmount is more than debt left, else true
     */
    public boolean payOffDebt(double payOffAmount) {
       if (debt.getOwed() - getSumOfPayments() > payOffAmount) {
           payments.add(new Payment(payOffAmount));
           return true;
       }
       return false;
    }

    /**
     * @return a list of pair containing payment history
     */
    @Override
    public List<IPaymentData> getPaymentHistory() {
        return new ArrayList<IPaymentData>(payments);
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
    public double getOwed() {
        return debt.getOwed();
    }


    @Override
    public Date getDate() {
        return debt.getDate();
    }
}
