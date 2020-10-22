package com.goayo.debtify.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.GroupNotFoundException;
import com.goayo.debtify.model.IDebtData;
import com.goayo.debtify.model.InvalidPaymentException;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Yenan Wang
 * @date 2020-09-29
 * <p>
 * ViewModel for SettleDebtFragment
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-14 Modified by Yenan Wang: Added getUnfinishedDebts() method to filter out the paid debts,
 * Changed super class to ModelEngineViewModel
 * 2020-10-16 Modified by Yenan Wang: Updated JavaDoc for all public methods
 */
public class SettleDebtViewModel extends ModelEngineViewModel {

    private final MutableLiveData<List<IDebtData>> debtListData;

    /**
     * Initialise the debtListData
     */
    public SettleDebtViewModel() {
        super();
        // initialise the debtListData since nothing else will initialise it
        List<IDebtData> initList = new ArrayList<>();
        debtListData = new MutableLiveData<>(initList);
    }

    /**
     * Retrieve a group that matches the given groupID and updates debtListData with all debts
     * that exist in that group
     *
     * @param groupID The current group's ID
     */
    public void retrieveData(String groupID) {
        try {
            // retrieve all debt that still can be paid into the list
            debtListData.setValue(getUnfinishedDebts(getModel().getGroup(groupID).getDebts()));
        } catch (Exception ignored) {
        }
    }

    /**
     * Retrieve the List of debts to be settled
     *
     * @return A copy of debtList in debtListData
     */
    public List<IDebtData> getDebtList() {
        return new ArrayList<>(Objects.requireNonNull(debtListData.getValue()));
    }

    /**
     * Create a new payment in the backend to the given Debt
     *
     * @param amount  The amount to be paid off
     * @param debtID  The ID of the Debt that is to be paid
     * @param groupID The ID of the Group the Debt belongs to
     * @throws Exception To be specified later
     */
    public void settleDebt(BigDecimal amount, String debtID, String groupID)
            throws InvalidPaymentException, GroupNotFoundException, ConnectException {
        getModel().payOffDebt(amount, debtID, groupID);
    }

    private List<IDebtData> getUnfinishedDebts(List<IDebtData> debts) {
        List<IDebtData> filteredDebts = new ArrayList<>();
        double epsilon = 0.01;
        for (IDebtData debt : debts) {
            // if a debt's left-over debt amount is more than epsilon
            // then it still can be paid
            // else it's a negligible amount of money and we yoink them
            if (debt.getAmountOwed().doubleValue() > epsilon) {
                filteredDebts.add(debt);
            }
        }
        return filteredDebts;
    }

}
