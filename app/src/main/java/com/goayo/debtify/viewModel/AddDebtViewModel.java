package com.goayo.debtify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yenan Wang
 * @date 2020-09-29
 * <p>
 * ViewModel for AddDebtFragment
 */
public class AddDebtViewModel extends ViewModel {

    private ModelEngine modelEngine = ModelEngine.getInstance();

    private MutableLiveData<Set<IUserData>> selectedLenderData;
    private MutableLiveData<Set<IUserData>> selectedBorrowersData;

    public LiveData<Set<IUserData>> getSelectedLenderData() {
        if (selectedLenderData == null) {
            selectedLenderData = new MutableLiveData<>();
        }
        return selectedLenderData;
    }

    public LiveData<Set<IUserData>> getSelectedBorrowersData() {
        if (selectedBorrowersData == null) {
            selectedBorrowersData = new MutableLiveData<>();
        }
        return selectedBorrowersData;
    }

    // TODO refactor all strings to IUserData or whatever
    public void createDebt(String groupID,
                           Set<IUserData> lender,
                           Set<IUserData> borrowers,
                           double amount,
                           String description) throws Exception {
        modelEngine.createDebt(groupID,
                // this is horrendous
                ((IUserData) (convertToString(lender).toArray()[0])).getPhoneNumber(),
                convertToString(borrowers), amount, description);
    }

    public void setSelectedLender(Set<IUserData> lender) {
        selectedLenderData.setValue(lender);
    }

    public void setSelectedBorrowersData(Set<IUserData> borrowers) {
        selectedBorrowersData.setValue(borrowers);
    }

    // TODO this method shouldn't be needed
    private Set<String> convertToString(Set<IUserData> userDataSet) {
        Set<String> userToString = new HashSet<>();
        for (IUserData user : userDataSet) {
            userToString.add(user.getPhoneNumber());
        }
        return userToString;
    }
}
