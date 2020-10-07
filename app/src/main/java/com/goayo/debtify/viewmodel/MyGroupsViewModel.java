package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.EventBus;
import com.goayo.debtify.model.GroupsEvent;
import com.goayo.debtify.model.IEventHandler;
import com.goayo.debtify.model.IModelEvent;
import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IGroupData;

import java.util.Set;

/**
 * View model class responsible for getting group data from the model and
 * handing it up to the views.
 *
 * @author Oscar Sanner and Alex Phu
 * @date 2020-09-25
 * <p>
 * 2020-09-30 Modified by Alex Phu and Yenan Wang: Cleaned up MyGroupsViewModel so it is only responsible for
 * fetching groupsdata.
 */

public class MyGroupsViewModel extends ViewModel implements IEventHandler {
    private MutableLiveData<Set<IGroupData>> groupsData;

    public MyGroupsViewModel() {
        super();
        Set<IGroupData> groupsData = ModelEngine.getInstance().getGroups();
        this.groupsData = new MutableLiveData<>(groupsData);
        EventBus.getInstance().register(this, GroupsEvent.class);
    }

    private void setGroupsData(){
        groupsData.setValue(ModelEngine.getInstance().getGroups());
    }

    public LiveData<Set<IGroupData>> getGroupsData() {
        return groupsData;
    }


    @Override
    public void onModelEvent(IModelEvent evt) {
        setGroupsData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getInstance().unRegister(this, GroupsEvent.class);
    }
}
