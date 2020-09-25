package com.goayo.debtify.model;


import com.goayo.debtify.modelaccess.IDebtData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Ledger holds a list DebtTracker and manages debts and payments
 * <p>
 * 2020-09-16 Modified by Gabriel & Yenan : Implemented methods.
 * 2020-09-17 Modified by Gabriel & Yenan : Changed to exception on createDebt. Added comments.
 * 2020-09-25 Modified by Olof Sjögren, Alex Phu & Oscar Sanner : Implemented getUsersTotal for
 * calculating a specific Users net total debt.
 */
class Ledger {

    private List<DebtTracker> debtTrackerList = new ArrayList<>();

    /**
     * Creates a debtTracker and adds it to the list of debtTrackers.
     *
     * @param lender    the user who lends out money
     * @param borrowers either a single or several users who borrow from the lender
     * @param owedTotal total amount lent out by the lender to the borrowers
     * @throws Exception
     */
    public void createDebt(User lender, Set<User> borrowers, double owedTotal) throws Exception {
        double individualAmount = owedTotal / borrowers.size();
        List<DebtTracker> mockList = new ArrayList<>();

        if (borrowers.size() == 0) {
            // TODO: Specify exception.
            throw new Exception();
        }

        for (User u : borrowers) {
            if (!mockList.add(new DebtTracker(individualAmount, lender, u))) {
                //TODO: Specify exception.
                throw new Exception();
            }
        }

        if (!debtTrackerList.addAll(mockList)) {
            //TODO: Specify exception.
            throw new Exception();
        }
    }

    /**
     * Adds a new payment to a specific debtTracker.
     *
     * @param amount        Amount being paid back against the debt.
     * @param debtTrackerID ID used to retrieve the specific debtTracker.
     * @throws
     */
    public void payOffDebt(double amount, String debtTrackerID) throws Exception {
        findDebtTracker(debtTrackerID).payOffDebt(amount);
    }

    private DebtTracker findDebtTracker(String debtTrackerID) {
        for (DebtTracker dt : debtTrackerList) {
            if (dt.getDebtID().equals(debtTrackerID)) {
                return dt;
            }
        }
        return null;
    }

    public IDebtData getDebtData(String debtID) {
        return findDebtTracker(debtID);
    }

    public List<IDebtData> getDebtDataList() {
        return new ArrayList<IDebtData>(debtTrackerList);
    }

    /**
     * Calculates the net total debt for a specific User.
     *
     * @param user The user for which the total debt calculations will be made.
     * @return the net total debt.
     */
    public double getUsersTotal(User user) {
        double total = 0;
        for (DebtTracker dt : debtTrackerList) {
            if (dt.getLender().equals(user)) {
                total += dt.getRemainingDebt();
            } else if (dt.getBorrower().equals(user)) {
                total -= dt.getRemainingDebt();
            }
        }
        return total;
    }
}
