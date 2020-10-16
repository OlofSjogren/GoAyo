package com.goayo.debtify.model;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Ledger holds a list DebtTracker and manages debts and payments
 * <p>
 * 2020-09-16 Modified by Gabriel Brattgård and Yenan Wang: Implemented methods.
 * 2020-09-17 Modified by Gabriel Brattgård and Yenan Wang: Changed to exception on createDebt. Added comments.
 * 2020-09-25 Modified by Olof Sjögren, Alex Phu and Oscar Sanner: Implemented getUsersTotal for
 * calculating a specific Users net total debt.
 * 2020-09-28 Modified by Yenan Wang: refactor to add parameter description to createDebt method
 * 2020-09-29 Modified by Olof Sjögren and Oscar Sanner: Created method for removing all debts of a specific user (removeSpecificUserDebt).
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-09 Modified by Alex Phu and Yenan Wang: Added IDebtSplitStrategy to createDebt's parameter.
 * 2020-10-11 Modified by Oscar Sanner: Added rounding mode for BigDecimal.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 */
class Ledger {

    private final List<DebtTracker> debtTrackerList = new ArrayList<>();

    /**
     * Creates a debtTracker and adds it to the list of debtTrackers.
     *
     * @param lender         the user who lends out money
     * @param borrowersAndId either a single or several users who borrow from the lender
     * @param owedTotal      total amount lent out by the lender to the borrowers
     * @param description    the brief description of the debt
     * @param splitStrategy  how the debt will be split (if at all) among the borrowers.
     * @param date
     * @throws DebtException thrown if the debt creation failed.
     */
    public void createDebt(User lender, Map<User, String> borrowersAndId, BigDecimal owedTotal, String description, IDebtSplitStrategy splitStrategy, Date date) throws DebtException {

        Map<User, Tuple<BigDecimal, String>> usersInDebt = splitStrategy.splitDebt(borrowersAndId, owedTotal);
        List<DebtTracker> debtList = new ArrayList<>();

        for (Map.Entry<User, Tuple<BigDecimal, String>> entry : usersInDebt.entrySet()) {
            if (!debtList.add(new DebtTracker(usersInDebt.get(entry.getKey()).getFirst(), lender, entry.getKey(), description, entry.getValue().getSecond(), date))) {
                throw new DebtException("Failed to create the debt.");
            }
        }

        if (!debtTrackerList.addAll(debtList)) {
            throw new DebtException("Failed to create the debt.");
        }
    }

    /**
     * Adds a new payment to a specific debtTracker.
     *  @param amount        Amount being paid back against the debt.
     * @param debtTrackerID ID used to retrieve the specific debtTracker.
     * @param date
     */
    public void payOffDebt(BigDecimal amount, String debtTrackerID, Date date) {
        findDebtTracker(debtTrackerID).payOffDebt(amount, date);
    }

    /**
     * Removes all debts associated to a specific user in a group. Method is used when a user is removed from a group.
     *
     * @param user the user who's debts are to be removed.
     */
    public void removeSpecificUserDebt(User user) {
        List<DebtTracker> newList = new ArrayList<>(debtTrackerList);
        for (DebtTracker dt : newList) {
            if (dt.getLender().equals(user) || dt.getBorrower().equals(user)) {
                debtTrackerList.remove(dt);
            }
        }
    }

    /**
     * Method for retrieving a specific debtTracker in the ledger.
     *
     * @param debtID the id of the debt to retrieve.
     * @return the debt tracker associated with the given id, wrapped in the IDebtData type.
     */
    public IDebtData getDebtData(String debtID) {
        return findDebtTracker(debtID);
    }

    /**
     * Method for retrieving the ledger's list of debt trackers.
     *
     * @return a new ArrayList with all debt trackers, each wrapped in the IDebtData type.
     */
    public List<IDebtData> getDebtDataList() {
        return new ArrayList<>(debtTrackerList);
    }

    /**
     * Calculates the net total debt for a specific User.
     *
     * @param user The user for which the total debt calculations will be made.
     * @return the net total debt. Positive if user is owed more money than the user owes money and negative if vice versa.
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

    /**
     * Method for finding a specific debt tracker given an id.
     *
     * @param debtTrackerID the id of the DebtTracker to find.
     * @return the DebtTracker with the given id. If a DebtTracker with the given id can't be found the method will return null.
     */
    private DebtTracker findDebtTracker(String debtTrackerID) {
        for (DebtTracker dt : debtTrackerList) {
            if (dt.getDebtID().equals(debtTrackerID)) {
                return dt;
            }
        }
        return null;
    }
}
