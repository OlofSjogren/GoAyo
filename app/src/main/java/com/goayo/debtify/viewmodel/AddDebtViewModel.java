package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.DebtSplitFactory;
import com.goayo.debtify.model.IDebtSplitStrategy;
import com.goayo.debtify.model.IUserData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yenan Wang
 * @date 2020-09-29
 * <p>
 * ViewModel for AddDebtFragment
 * <p>
 * 2020-09-30 Modified by Yenan & Alex: Fix an error caused by createDebt method
 * <p>
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * <p>
 * 2020-10-09 Modified by Yenan Wang and Alex Phu: Added IDebtStrategy to createDebt()
 * <p>
 * 2020-10-12 Modified by Alex Phu: Instantiation of ISplitStrategy are now done through DebtSplitFactory
 *
 */
public class AddDebtViewModel extends ModelEngineViewModel {

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
     * @param lender the Set of lender that replaces the current Set of lender,
     *               the Set should contain only one element at most
     */
    public void setSelectedLenderData(Set<IUserData> lender) {
        selectedLenderData.setValue(lender);
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
     * creates a new Debt in the model
     *
     * @param groupID     the ID of the group to create a debt in
     * @param lender      the lender
     * @param borrowers   the borrower/borrowers
     * @param amount      the total amount of the debt
     * @param description a brief description of the debt
     * @param isNoSplit   How the debt will be split
     * @throws Exception to be specified later
     */
    public void createDebt(String groupID,
                           Set<IUserData> lender,
                           Set<IUserData> borrowers,
                           BigDecimal amount,
                           String description,
                           boolean isNoSplit) throws Exception {
        // TODO refactor all strings to IUserData
        IDebtSplitStrategy splitStrategy;
        if (isNoSplit) {
            splitStrategy = DebtSplitFactory.createNoSplitStrategy();
        } else {
            splitStrategy = DebtSplitFactory.createEvenSplitStrategy();
        }
        getModel().createDebt(groupID,
                // this is horrendous
                (new ArrayList<>(lender).get(0)).getPhoneNumber(),
                convertToString(borrowers), amount, description,
                splitStrategy);
    }

    public Set<IUserData> getGroupMembers(String groupID) throws Exception {
        return getModel().getGroup(groupID).getIUserDataSet();
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
