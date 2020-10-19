package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.EventBus;
import com.goayo.debtify.model.IEventHandler;
import com.goayo.debtify.model.IGroupData;

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
 * 2020-10-08 Modified by Alex Phu: Added getCurrentLoggedInUsersPhoneNumber() for GroupViewAdapter. To be able to get
 * UserTotal in each group.
 * 2020-10-12 Modified by Olof Sjögren: Added getCurrentLoggedInUserName for getting logged in users name.
 * 2020-10-12 Modified by Alex Phu: Implemented updateGroupsFromDatabase()
 * 2020-10-14 Modified by Yenan Wang: Changed super class to ModelEngineViewModel
 * 2020-10-16 Modified by Yenan Wang: Updated JavaDoc for all public methods
 */

public class MyGroupsViewModel extends ModelEngineViewModel implements IEventHandler {

    private MutableLiveData<Set<IGroupData>> groupsData;

    /**
     * Initiate the groupsData by retrieving all associated groups from the model, and subscribes
     * itself to GROUPS_EVENT so that it can be notifies of all updates to that specific event immediately
     */
    public MyGroupsViewModel() {
        super();
        Set<IGroupData> groupsData = getModel().getGroups();
        this.groupsData = new MutableLiveData<>(groupsData);
        EventBus.getInstance().register(this, EventBus.EVENT.GROUPS_EVENT);
    }

    /**
     * Get the LiveData object so any views that uses this method may observe the LiveData
     * and update themselves accordingly
     *
     * @return The LiveData object representing a Set of groups
     */
    public LiveData<Set<IGroupData>> getGroupsData() {
        return groupsData;
    }

    /**
     * Update the groupsData by retrieving all associated groups in the model
     */
    @Override
    public void onModelEvent() {
        updateGroupsData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getInstance().unRegister(this, EventBus.EVENT.GROUPS_EVENT);
    }

    // TODO combine these two methods into getLoggedInUser()
    /**
     * Retrieve the phone number of the logged-in user
     *
     * @return The phone number of the logged-in user
     */
    public String getCurrentLoggedInUsersPhoneNumber() {
        return getModel().getLoggedInUser().getPhoneNumber();
    }

    /**
     * Retrieve the name of the logged-in user
     *
     * @return The name of the logged-in user
     */
    public String getCurrentLoggedInUserName() {
        return getModel().getLoggedInUser().getName();
    }

    /**
     * Update data from database
     */
    public void updateGroupsFromDatabase() {
        try {
            getModel().refreshWithDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateGroupsData() {
        groupsData.setValue(getModel().getGroups());
    }
}
