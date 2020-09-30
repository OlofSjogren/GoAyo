package com.goayo.debtify.model;


import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IPaymentData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * DebtTracker holds debts and keeps track of debt
 * 2020-09-17 Modified by Yenan & Gabriel : Updated comments. Added exception on payOffDebt instead of boolean false as return.
 * 2020-09-25 Modified by Olof Sjögren, Alex Phu & Oscar Sanner : Added getRemainingDebt for calculating remaining debt.
 * 2020-09-28 Modified by Yenan : refactor to add description parameter to the constructor
 * 2020-09-29 Modified by Oscar Sanner and Olof Sjögren: Replaced "tempID" with a proper dynamically generated UUID.
 * Also fixed bug in payOffDebt(), now compares larger than AND EQUAL to instead of just larger than.
 */
class DebtTracker implements IDebtData {
    private final Debt debt;
    private final List<Payment> payments;
    private final User lender;
    private final User borrower;
    private final String description;
    private final String debtTrackerID;

    /**
     * constructor creates a debt and assigns lender and borrower
     *
     * @param debtAmount  how much the borrower owe to lender
     * @param lender      the user that lends out money
     * @param borrower    the user that borrows money
     * @param description the brief description of the debt
     */
    public DebtTracker(double debtAmount, User lender, User borrower, String description) {
        this.debt = new Debt(debtAmount);
        this.payments = new ArrayList<>();
        this.lender = lender;
        this.borrower = borrower;
        this.debtTrackerID = UUID.randomUUID().toString();
        this.description = description;
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
        this.description = debtTracker.description;
        this.debtTrackerID = debtTracker.debtTrackerID;
    }

    /**
     * Adds a new payment to the list of payments
     *
     * @param payOffAmount the amount to pay off
     * @throws if payOffAmount exceeds debt left to pay
     */
    public void payOffDebt(double payOffAmount) throws Exception {
        if (debt.getDebtAmount() - getSumOfPayments() >= payOffAmount) {
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
     * @return a list of all payment history
     */
    @Override
    public List<IPaymentData> getPaymentHistory() {
        return new ArrayList<IPaymentData>(payments);
    }


    /**
     * Calculates remaining debt.
     *
     * @return the remaining amount to be payed.
     */
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
    public String getDescription() {
        return description;
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
