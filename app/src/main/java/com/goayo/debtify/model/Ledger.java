package com.goayo.debtify.model;

import java.util.List;

public class Ledger {
    public boolean createDebt(User lender, List<User> borrower, double owed) {
        return false;
    }

    public boolean payOffDebt(double amount, String debtID) {
        return true;
    }
}
