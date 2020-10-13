package com.goayo.debtify.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.IDebtData;

import java.math.BigDecimal;
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
 */
public class SettleDebtViewModel extends ModelEngineViewModel {

    private MutableLiveData<List<IDebtData>> debtListData;

    /**
     * the constructor,
     * initialises the debtListData at creation
     */
    public SettleDebtViewModel() {
        super();
        // initialise the debtListData since nothing else will initialise it
        List<IDebtData> initList = new ArrayList<>();
        debtListData = new MutableLiveData<>(initList);
    }

    /**
     * loads data from the model into debtListData from the given groupID
     *
     * @param groupID the current group's ID
     */
    public void retrieveData(String groupID) {
        try {
            debtListData.setValue(getModel().getGroup(groupID).getDebts());
        } catch (Exception ignored) {
        }
    }

    /**
     * @return the copy of debtList
     */
    public List<IDebtData> getDebtList() {
        return new ArrayList<>(Objects.requireNonNull(debtListData.getValue()));
    }

    /**
     * creates a new payment in the backend to the given Debt
     *
     * @param amount  the amount to be paid off
     * @param debtID  the ID of the Debt that is to be paid
     * @param groupID the ID of the Group the Debt belongs to
     * @throws Exception to be specified later
     */
    public void settleDebt(BigDecimal amount, String debtID, String groupID) throws Exception {
        getModel().payOffDebt(amount, debtID, groupID);
    }

}
