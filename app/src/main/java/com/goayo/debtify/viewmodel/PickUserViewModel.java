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
    private MutableLiveData<List<IUserData>> selectedUsersData;
    private MutableLiveData<IGroupData> currentGroup;

    /**
     * Constructor for PickUserViewModel
     */
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

    public void setPotentialUsersData() {
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

    public LiveData<List<IUserData>> getPotentialUsersData() {
        if(potentialUsersToBeAddedData == null) {
            Log.d("PickUserViewModel", "getPotentialUsersData: Returnes null");
            return null;
        }
        return potentialUsersToBeAddedData;
    }

    /**
     * Adds a user to selectedUsersData if it doesn't contain it, otherwise remove.
     * Called from CardViews in PickUserAdapter
     * @param user User to be added
     */
    public void setSelectedUsersData(IUserData user) {
        if(selectedUsersData == null) {
            selectedUsersData = new MutableLiveData<List<IUserData>>();
        }
        List<IUserData> temporaryUserList  = selectedUsersData.getValue();
        if(!selectedUsersData.getValue().contains(user)){
            temporaryUserList.add(user);
        }else{
            //Unselect user
            temporaryUserList.remove(user);
        }
        selectedUsersData.setValue(temporaryUserList);
    }

    public LiveData<List<IUserData>> getSelectedUsersData() {
        if(selectedUsersData == null) {
            Log.d("PickUserViewModel", "getSelectedUsersData: Returns null");
            return null;
        }
        return selectedUsersData;
    }

    public void setUsersToBeRemoved() {
        //TODO ("FUTURE USER STORY")
    }
}
