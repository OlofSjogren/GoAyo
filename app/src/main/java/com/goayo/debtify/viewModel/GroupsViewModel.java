package com.goayo.debtify.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IGroupData;

import java.util.Set;

/**
 * View model class responsible for getting group data from the model and
 * handing it up to the views.
 *
 * @author Oscar Sanner and Alex Phu
 * @date 2020-09-25
 */

public class GroupsViewModel extends ViewModel {
    private MutableLiveData<Set<IGroupData>> groupData;
    private MutableLiveData<IGroupData> currentGroup;
    private MutableLiveData<IDebtData[]> currentGroupsDebtData;

    public LiveData<Set<IGroupData>> getAllGroupsData() {
        if (groupData == null) {
            groupData = new MutableLiveData<Set<IGroupData>>();
            Set<IGroupData> dataFromModel = ModelEngine.getInstance().getGroups();
            groupData.setValue(dataFromModel);
        }
        return groupData;
    }

    public void setAllGroupsData(Set<IGroupData> groupData) {
        this.groupData.setValue(groupData);
    }

    public LiveData<IGroupData> getCurrentGroupData() {
        if (currentGroup == null) {
            throw new NullPointerException("CURRENT GROUP IS NOT SET! WE'RE DOING SOMETHING WRONG");
        }
        return currentGroup;
    }

    public void setCurrentGroup(String groupId) {
        if (currentGroup == null) {
            currentGroup = new MutableLiveData<IGroupData>();
        }
        try {
            currentGroup.setValue(ModelEngine.getInstance().getGroup(groupId));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("GroupsViewModel", "setCurrentGroup: DATABASE ERROR?");
        }
    }

    public LiveData<IDebtData[]> getDebt() {
        if (currentGroupsDebtData == null) {
            throw new NullPointerException("THE DEBT DATA IS NULL, WE HAVE DONE SOMETHING WRONG");
        } else {
            return currentGroupsDebtData;
        }
    }

    public void setCurrentGroupsDebtData() {
        if (currentGroupsDebtData == null) {
            currentGroupsDebtData = new MutableLiveData<>();
        }
        currentGroupsDebtData.setValue(fetchDebtData(currentGroup.getValue()));

    }

    //Todo split to groups and debts maybe.
    private IDebtData[] fetchDebtData(IGroupData groupData) {
        //TODO ("IGroupData currently has the type List, change to Set or keep?")
        // If kept, change remove convertSetToArray() method.
        IDebtData[] debtData = new IDebtData[groupData.getDebts().size()];
        return groupData.getDebts().toArray(debtData);
    }
}
