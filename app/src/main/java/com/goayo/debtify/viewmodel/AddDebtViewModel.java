package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.DebtException;
import com.goayo.debtify.model.DebtSplitFactory;
import com.goayo.debtify.model.GroupNotFoundException;
import com.goayo.debtify.model.IDebtSplitStrategy;
import com.goayo.debtify.model.IUserData;
import com.goayo.debtify.model.UserNotFoundException;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author Yenan Wang
 * @date 2020-09-29
 * <p>
 * ViewModel for AddDebtFragment
 * <p>
 * 2020-09-30 Modified by Yenan Wang & Alex Phu: Fix an error caused by createDebt method
 * 2020-10-05 Modified by Oscar Sanner & Olof Sjögren: Switched all them doubles to them BigDecimals
 * and made sure all the return types and params of methods are correctly set as BigDecimal.
 * 2020-10-09 Modified by Yenan Wang & Alex Phu: Added IDebtStrategy to createDebt()
 * 2020-10-12 Modified by Alex Phu: Instantiation of ISplitStrategy are now done through DebtSplitFactory
 * 2020-10-14 Modified by Yenan Wang: Changed super class to ModelEngineViewModel
 * 2020-10-16 Modified by Yenan Wang: Updated JavaDoc for all public methods
 */
public class AddDebtViewModel extends ModelEngineViewModel {

    private MutableLiveData<Set<IUserData>> selectedLenderData;
    private MutableLiveData<Set<IUserData>> selectedBorrowersData;

    /**
     * Get the LiveData object so any views that uses this method may observe the LiveData
     * and update themselves accordingly
     *
     * @return The LiveData object representing the Set of lender
     */
    public LiveData<Set<IUserData>> getSelectedLenderData() {
        if (selectedLenderData == null) {
            selectedLenderData = new MutableLiveData<>();
        }
        return selectedLenderData;
    }

    /**
     * Set a Set of IUserData to selectedLenderData and notifies everyone that observes this
     * LiveData of the update, the Set should contain only one element at most
     *
     * @param lender The Set of lender that replaces the current Set of lender
     */
    public void setSelectedLenderData(Set<IUserData> lender) {
        selectedLenderData.setValue(lender);
    }

    /**
     * Get the LiveData object so any views that uses this method may observe the LiveData
     * and update themselves accordingly
     *
     * @return The LiveData object representing the Set of borrowers
     */
    public LiveData<Set<IUserData>> getSelectedBorrowersData() {
        if (selectedBorrowersData == null) {
            selectedBorrowersData = new MutableLiveData<>();
        }
        return selectedBorrowersData;
    }

    /**
     * Set a Set of IUserData to selectedBorrowersData and notifies everyone that observes this
     * LiveData of the update
     *
     * @param borrowers The Set of borrowers that replaces the current Set of borrowers
     */
    public void setSelectedBorrowersData(Set<IUserData> borrowers) {
        selectedBorrowersData.setValue(borrowers);
    }

    /**
     * Create a Debt in the model with the given parameters
     *
     * @param groupID     The ID of the group to create a debt in
     * @param lender      The lender
     * @param borrowers   The borrower/borrowers
     * @param amount      The total amount of the debt
     * @param description A brief description of the debt
     * @param isNoSplit   How the debt will be split
     * @throws GroupNotFoundException thrown if the group with the given groupID can't be found in the database or in the list "associated groups".
     * @throws UserNotFoundException  thrown if the lender or borrowers are not members of the group.
     * @throws DebtException          thrown if the creation of the debt failed.
     * @throws ConnectException       thrown if unable to connect to the database.
     */
    public void createDebt(String groupID,
                           Set<IUserData> lender,
                           Set<IUserData> borrowers,
                           BigDecimal amount,
                           String description,
                           boolean isNoSplit)
            throws UserNotFoundException, ConnectException, GroupNotFoundException, DebtException {

        // decide how to split the debt
        IDebtSplitStrategy splitStrategy;
        if (isNoSplit) {
            splitStrategy = DebtSplitFactory.createNoSplitStrategy();
        } else {
            splitStrategy = DebtSplitFactory.createEvenSplitStrategy();
        }

        // create the debts
        getModel().createDebt(groupID, (new ArrayList<>(lender).get(0)).getPhoneNumber(),
                ViewModelUtil.convertToUserPhoneNumberSet(borrowers),
                amount, description, splitStrategy);
    }

    /**
     * Retrieve a Set of all group members in the group that matches the groupID
     *
     * @param groupID ID that belongs to a group
     * @return All users who are a member of the group that matches the groupID
     */
    public Set<IUserData> getGroupMembers(String groupID) throws GroupNotFoundException {
        return getModel().getGroup(groupID).getIUserDataSet();
    }

}
