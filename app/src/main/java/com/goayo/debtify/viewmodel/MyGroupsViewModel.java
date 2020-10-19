package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.EventBus;
import com.goayo.debtify.model.IEventHandler;
import com.goayo.debtify.model.IGroupData;
import com.goayo.debtify.model.ModelEngine;

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
 * <p>
 * 2020-10-08 Modified by Alex Phu: Added getCurrentLoggedInUsersPhoneNumber() for GroupViewAdapter. To be able to get
 * UserTotal in each group.
 * 2020-10-12 Modified by Olof Sjögren: Added getCurrentLoggedInUserName for getting logged in users name.
 * <p>
 * 2020-10-12 Modified by Alex Phu: Implemented updateGroupsFromDatabase()
 */

public class MyGroupsViewModel extends ModelEngineViewModel implements IEventHandler {

    private MutableLiveData<Set<IGroupData>> groupsData;

    public MyGroupsViewModel() {
        super();
        Set<IGroupData> groupsData = getModel().getGroups();
        this.groupsData = new MutableLiveData<>(groupsData);
        EventBus.INSTANCE.register(this, EventBus.EVENT.GROUPS_EVENT);
    }

    private void setGroupsData() {
        groupsData.setValue(getModel().getGroups());
    }

    public LiveData<Set<IGroupData>> getGroupsData() {
        return groupsData;
    }

    @Override
    public void onModelEvent() {
        setGroupsData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.INSTANCE.unRegister(this, EventBus.EVENT.GROUPS_EVENT);
    }

    public String getCurrentLoggedInUsersPhoneNumber() {
        return getModel().getLoggedInUser().getPhoneNumber();
    }

    public String getCurrentLoggedInUserName() {
        return getModel().getLoggedInUser().getName();
    }

    public void updateGroupsFromDatabase() {
        try {
            getModel().refreshWithDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
