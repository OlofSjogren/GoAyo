package com.goayo.debtify.model;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * DebtTracker holds debts and keeps track of debt
 */
public class DebtTracker {
    private final Debt debt;
    private final List<Payment> payments;
    private final User lender;
    private final User borrower;
    private final String debtTrackerID;

    public DebtTracker(Debt debt, User lender, User borrower) {
        this.debt = debt;
        this.payments = new ArrayList<>();
        this.lender = lender;
        this.borrower = borrower;
        this.debtTrackerID = "TEMP ID";
    }

    public boolean createDebt(User lender, User borrower, double owed) {
        return false;
    }

    public boolean payOffDebt(double amount) {
        return false;
    }
}
