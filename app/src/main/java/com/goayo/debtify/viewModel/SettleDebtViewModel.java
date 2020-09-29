package com.goayo.debtify.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IDebtData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Yenan Wang
 * @date 2020-09-29
 * <p>
 * ViewModel for SettleDebtFragment
 */
public class SettleDebtViewModel extends ViewModel {

    private ModelEngine modelEngine = ModelEngine.getInstance();

    private MutableLiveData<List<IDebtData>> debtListData;

    public SettleDebtViewModel() {
        super();
        // initialise the debtListData since nothing else will initialise it
        List<IDebtData> initList = new ArrayList<>();
        debtListData = new MutableLiveData<>(initList);
    }

    public void retrieveData(String groupID) {
        try {
            debtListData.setValue(modelEngine.getGroup(groupID).getDebts());
        } catch (Exception ignored) {
        }
    }

    public List<IDebtData> getDebtList() {
        return new ArrayList<>(Objects.requireNonNull(debtListData.getValue()));
    }

    public void settleDebt(double amount, String debtID, String groupID) throws Exception {
        modelEngine.payOffDebt(amount, debtID, groupID);
    }

}
