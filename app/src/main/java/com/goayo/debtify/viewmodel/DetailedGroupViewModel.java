package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.EventBus;
import com.goayo.debtify.model.IDebtData;
import com.goayo.debtify.model.IEventHandler;
import com.goayo.debtify.model.IGroupData;
import com.goayo.debtify.model.IUserData;
import com.goayo.debtify.model.UserNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Phu, Oscar Sanner
 * @date 2020-09-29
 * <p>
 * ViewModel for the DetailedGroupActivity
 * <p>
 * 2020-09-30 Modified by Alex Phu & Yenan Wang: Add getAddableUser method
 * 2020-10-08 Modified by Alex Phu: Added leaveCurrentGroup() method
 * 2020-10-09 Modified by Yenan Wang & Alex Phu: connect with EventBus and divide up the currentGroup LiveData
 * so that it may observed individually
 * 2020-10-12 Modified by Alex Phu: Implemented updateDataFromDatabase() and refactored incomprehensible methods.
 * 2020-10-14 Modified by Yenan Wang: Changed super class to ModelEngineViewModel
 * 2020-10-16 Modified by Yenan Wang: Updated JavaDoc for all public methods
 */
public class DetailedGroupViewModel extends ModelEngineViewModel implements IEventHandler {

    private MutableLiveData<IGroupData> currentGroup;
    private MutableLiveData<List<IDebtData>> currentGroupDebtsData;
    private MutableLiveData<BigDecimal> currentGroupBalance;

    /**
     * Subscribe itself to the SPECIFIC_GROUP_EVENT so that it can be notifies of all updates to
     * that specific event immediately
     */
    public DetailedGroupViewModel() {
        EventBus.getInstance().register(this, EventBus.EVENT.SPECIFIC_GROUP_EVENT);
    }

    /**
     * Get the LiveData object so any views that uses this method may observe the LiveData
     * and update themselves accordingly
     *
     * @return The LiveData object representing the current group
     */
    public LiveData<IGroupData> getCurrentGroup() {
        if (currentGroup == null) {
            currentGroup = new MutableLiveData<>();
        }
        return currentGroup;
    }

    /**
     * Retrieve the group that matches the given groupID, then sets the retrieve group to the
     * currentGroup and notifies everyone that observes this LiveData of the update
     *
     * @param groupID The groupID of the current group
     */
    public void setCurrentGroup(String groupID)
    // TODO remove this Exception throws signature
            throws Exception {
        if (currentGroup == null) {
            currentGroup = new MutableLiveData<>();
        }
        currentGroup.setValue(getModel().getGroup(groupID));

        // after the group is set, initialize the group components and fill them with data
        setCurrentGroupBalance();
        setCurrentGroupDebtsData();
    }

    /**
     * Get the LiveData object so any views that uses this method may observe the LiveData
     * and update themselves accordingly
     *
     * @return The LiveData object representing a List of Debt that belongs to the current group
     */
    public LiveData<List<IDebtData>> getCurrentGroupDebtsData() {
        if (currentGroupDebtsData == null) {
            currentGroupDebtsData = new MutableLiveData<>();
        }
        return currentGroupDebtsData;
    }

    /**
     * Get the LiveData object so any views that uses this method may observe the LiveData
     * and update themselves accordingly
     *
     * @return The LiveData object representing the logged-in user's balance in the current group
     */
    public LiveData<BigDecimal> getCurrentGroupBalance() {
        if (currentGroupBalance == null) {
            currentGroupBalance = new MutableLiveData<>();
        }
        return currentGroupBalance;
    }

    /**
     * Exclude all users who already are a member in the current group from the contact list and
     * return all users that are left
     *
     * @return All users that may still be added to the current group
     */
    public List<IUserData> getAddableUsers() {
        List<IUserData> addableUsers = new ArrayList<>();
        for (IUserData contact : getModel().getContacts()) {
            boolean userExists = false;
            for (IUserData groupMember : currentGroup.getValue().getIUserDataSet()) {
                if (contact.equals(groupMember)) {
                    userExists = true;
                    break;
                }
            }
            if (!userExists) {
                addableUsers.add(contact);
            }
        }
        return addableUsers;
    }

    /**
     * Given a Set of users, add them to the current group,
     * assuming if the current group is already set
     *
     * @param users The users to be added into current group
     */
    public void addSelectedMembersToCurrentGroup(Set<IUserData> users) {
        // ignore if the Set doesn't hold any user
        // this if-statement should never evaluate true
        if (users == null || users.isEmpty()) {
            return;
        }
        for (IUserData user : users) {
            if (!currentGroup.getValue().getIUserDataSet().contains(user)) {
                try {
                    getModel().addUserToGroup(user.getPhoneNumber(), currentGroup.getValue().getGroupID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Leave the current group.
     */
    public void leaveCurrentGroup() {
        try {
            getModel().leaveGroup(currentGroup.getValue().getGroupID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update data from database
     */
    public void updateDataFromDatabase() {
        try {
            getModel().refreshWithDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update currentGroup by retrieving the current group from the model
     */
    @Override
    public void onModelEvent() {
        try {
            setCurrentGroup(currentGroup.getValue().getGroupID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * When this ViewModel dies, unsubscribe itself from the EventBus
     */
    @Override
    protected void onCleared() {
        EventBus.getInstance().unRegister(this, EventBus.EVENT.SPECIFIC_GROUP_EVENT);
        super.onCleared();
    }

    private void setCurrentGroupDebtsData() {
        if (currentGroupDebtsData == null) {
            currentGroupDebtsData = new MutableLiveData<>();
        }
        currentGroupDebtsData.setValue(new ArrayList<>(currentGroup.getValue().getDebts()));
    }

    private void setCurrentGroupBalance() {
        if (currentGroupBalance == null) {
            currentGroupBalance = new MutableLiveData<>();
        }
        try {
            currentGroupBalance.setValue(new BigDecimal(currentGroup.getValue().
                    getUserTotal(getModel().getLoggedInUser().getPhoneNumber()).toString()));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
