package com.goayo.debtify.model;


import com.goayo.debtify.modelaccess.IDebtData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
 * 2020-09-28 Modified by Yenan: refactor to add parameter description to createDebt method
 * 2020-09-29 Modified by Olof & Oscar : Created method for removing all debts of a specific user (removeSpecificUserDebt).
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 */
class Ledger {

    private List<DebtTracker> debtTrackerList = new ArrayList<>();

    /**
     * Creates a debtTracker and adds it to the list of debtTrackers.
     *
     * @param lender    the user who lends out money
     * @param borrowers either a single or several users who borrow from the lender
     * @param owedTotal total amount lent out by the lender to the borrowers
     * @param description the brief description of the debt
     * @throws Exception
     */
    public void createDebt(User lender, Map<User, String> borrowers, BigDecimal owedTotal, String description) {
        BigDecimal individualAmount = owedTotal.divide(new BigDecimal(borrowers.size()));
        List<DebtTracker> mockList = new ArrayList<>();

        if (borrowers.size() == 0) {
            // TODO: Specify exception.
            throw new RuntimeException();
        }

        for (Map.Entry<User, String> entry : borrowers.entrySet()) {
            if (!mockList.add(new DebtTracker(individualAmount, lender, entry.getKey(), description, entry.getValue()))) {
                //TODO: Specify exception.
                throw new RuntimeException();
            }
        }

        if (!debtTrackerList.addAll(mockList)) {
            //TODO: Specify exception.
            throw new RuntimeException();
        }
    }

    /**
     * Adds a new payment to a specific debtTracker.
     *
     * @param amount        Amount being paid back against the debt.
     * @param debtTrackerID ID used to retrieve the specific debtTracker.
     * @throws
     */
    public void payOffDebt(BigDecimal amount, String debtTrackerID) {
        findDebtTracker(debtTrackerID).payOffDebt(amount);
    }

    /**
     * Removes all debts associated to a specific user in a group. Method is used when a user is removed from a group.
     *
     * @param user the user who's debts are to be removed.
     */
    public void removeSpecificUserDebt(User user){
        List<DebtTracker> newList = new ArrayList<>(debtTrackerList);
        for (DebtTracker dt : newList){
            if (dt.getLender().equals(user) || dt.getBorrower().equals(user)){
                debtTrackerList.remove(dt);
            }
        }
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
    public BigDecimal getUserTotal(User user) {
        BigDecimal total = new BigDecimal(0);
        for (DebtTracker dt : debtTrackerList) {
            if (dt.getLender().equals(user)) {
                total = total.add(dt.getAmountOwed());
            } else if (dt.getBorrower().equals(user)) {
                total = total.subtract(dt.getAmountOwed());
            }
        }
        return total;
    }
}
