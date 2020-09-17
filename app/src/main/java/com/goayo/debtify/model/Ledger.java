package com.goayo.debtify.model;


import com.goayo.debtify.modelaccess.IDebtData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Ledger holds a list DebtTracker and manages debts and payments
 *
 * 2020-09-16 Modified by Gabriel & Yenan : Implemented methods.
 */
class Ledger {

    // TODO: Add comments on all methods.

    private List<DebtTracker> debtTrackerList = new ArrayList<>();

    public boolean createDebt(User lender, List<User> borrowers, double owedTotal){
        double individualAmount = owedTotal/borrowers.size();
        List<DebtTracker> mockList = new ArrayList<>();

        for(int i=0; i<borrowers.size(); i++){
            if (!mockList.add(new DebtTracker(individualAmount, lender, borrowers.get(i)))) {
                return false;
            }
        }

        return debtTrackerList.addAll(mockList);
    }

    public IDebtData getDebtData(String debtID){
        return findDebtTracker(debtID);
    }

    public List<IDebtData> getDebtDataList(){
        return new ArrayList<IDebtData>(debtTrackerList);
    }

    public boolean payOffDebt(double amount, String debtTrackerID){
        return findDebtTracker(debtTrackerID).payOffDebt(amount);
    }

    private DebtTracker findDebtTracker(String debtTrackerID){
        for(DebtTracker dt : debtTrackerList){
            if(dt.getDebtID().equals(debtTrackerID)){
                return dt;
            }
        }
        return null;
    }
}
