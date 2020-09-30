package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
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

    /**
     * @return the LiveData object representing the Set of lender,
     * the Set should contain only one element at most
     */
    public LiveData<Set<IUserData>> getSelectedLenderData() {
        if (selectedLenderData == null) {
            selectedLenderData = new MutableLiveData<>();
        }
        return selectedLenderData;
    }

    /**
     * @return the LiveData object representing the Set of borrowers
     */
    public LiveData<Set<IUserData>> getSelectedBorrowersData() {
        if (selectedBorrowersData == null) {
            selectedBorrowersData = new MutableLiveData<>();
        }
        return selectedBorrowersData;
    }

    /**
     * @param borrowers the Set of borrowers that replaces the current Set of borrowers
     */
    public void setSelectedBorrowersData(Set<IUserData> borrowers) {
        selectedBorrowersData.setValue(borrowers);
    }

    /**
     * @param lender the Set of lender that replaces the current Set of lender,
     *               the Set should contain only one element at most
     */
    public void setSelectedLenderData(Set<IUserData> lender) {
        selectedLenderData.setValue(lender);
    }

    /**
     * creates a new Debt in the model
     *
     * @param groupID     the ID of the group to create a debt in
     * @param lender      the lender
     * @param borrowers   the borrower/borrowers
     * @param amount      the total amount of the debt
     * @param description a brief description of the debt
     * @throws Exception to be specified later
     */
    public void createDebt(String groupID,
                           Set<IUserData> lender,
                           Set<IUserData> borrowers,
                           double amount,
                           String description) throws Exception {
        // TODO refactor all strings to IUserData
        modelEngine.createDebt(groupID,
                // this is horrendous
                (new ArrayList<>(lender).get(0)).getPhoneNumber(),
                convertToString(borrowers), amount, description);
    }

    public Set<IUserData> getGroupMembers(String groupID) throws Exception {
        return modelEngine.getGroup(groupID).getIUserDataSet();
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
