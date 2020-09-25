package com.goayo.debtify.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IGroupData;

import java.util.Set;

/**
 * View model class responsible for getting group data from the model and
 * handing it up to the views.
 *
 * @author Oscar Sanner and Alex Phu
 * @date 2020-09-25
 * 
 */

public class GroupsViewModel extends ViewModel {
    private MutableLiveData<Set<IGroupData>> groupData;
    private MutableLiveData<IGroupData> currentGroup;

    public LiveData<Set<IGroupData>> getAllGroupsData(){
        if (groupData == null){
            groupData = new MutableLiveData<Set<IGroupData>>();
            Set<IGroupData> dataFromModel = ModelEngine.getInstance().getGroups();
            groupData.setValue(dataFromModel);
        }
        return groupData;
    }
    public void setAllGroupsData(Set<IGroupData> groupData){
        this.groupData.setValue(groupData);
    }

    public LiveData<IGroupData> getCurrentGroupData(){
        if (currentGroup == null){
            throw new NullPointerException("CURRENT GROUP IS NOT SET! WE'RE DOING SOMETHING WRONG");
        }
        return currentGroup;
    }

    public void setCurrentGroup(String groupId) {
        try {
            ModelEngine.getInstance().getGroup(groupId);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("GroupsViewModel", "setCurrentGroup: DATABASE ERROR?");
        }
    }
}
