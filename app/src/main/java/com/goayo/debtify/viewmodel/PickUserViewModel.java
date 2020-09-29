package com.goayo.debtify.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Phu
 * @date 2020-09-29
 *
 * ViewModel for PickUserFragment, and PickUserAdapter.
 */
public class PickUserViewModel extends ViewModel {
    private final ModelEngine modelEngine;

    private MutableLiveData<List<IUserData>> potentialUsersToBeAddedData;
    private MutableLiveData<IGroupData> currentGroup;

    public PickUserViewModel() {
        modelEngine = ModelEngine.getInstance();
    }

    /**
     * Sets the current group.
     * @param groupId GroupId of current group.
     */
    public void setCurrentGroup(String groupId) {
        if (currentGroup == null) {
            currentGroup = new MutableLiveData<IGroupData>();
        }
        try {
            currentGroup.setValue(ModelEngine.getInstance().getGroup(groupId));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("PickUserViewModel", "setCurrentGroup: DATABASE ERROR?");
        }
    }

    public void setUsersToBeAdded() {
        if(potentialUsersToBeAddedData == null) {
            potentialUsersToBeAddedData = new MutableLiveData<List<IUserData>>();
        }
        List<IUserData> temporaryUserList = new ArrayList<>();
        for (IUserData u : modelEngine.getContacts()) {
            if (!currentGroup.getValue().getIUserDataSet().contains(u)) {
                temporaryUserList.add(u);
            }
        }
        potentialUsersToBeAddedData.setValue(temporaryUserList);
    }

    public LiveData<List<IUserData>> getUsersToBeAdded() {
        if(potentialUsersToBeAddedData == null) {
            Log.d("PickUserViewModel", "Returnes null in getUserToBeAdded()");
            return null;
        }
        return potentialUsersToBeAddedData;
    }

    public void setUsersToBeRemoved() {
        //TODO ("FUTURE USER STORY")
    }
}
