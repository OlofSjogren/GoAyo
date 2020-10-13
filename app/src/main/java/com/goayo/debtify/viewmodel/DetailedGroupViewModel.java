package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.EventBus;
import com.goayo.debtify.model.IEventHandler;
import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.model.IDebtData;
import com.goayo.debtify.model.IGroupData;
import com.goayo.debtify.model.IUserData;

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
 * <p>
 * 2020-10-08 Modified by Alex Phu: Added leaveCurrentGroup() method
 * <p>
 * 2020-10-09 Modified by Yenan Wang & Alex Phu: connect with EventBus and divide up the currentGroup LiveData
 * so that it may observed individually
 * <p>
 * 2020-10-12 Modified by Alex Phu: Implemented updateDataFromDatabase() and refactored incomprehensible methods.
 */
public class DetailedGroupViewModel extends ViewModel implements IEventHandler {
    private final ModelEngine modelEngine;
    private MutableLiveData<IGroupData> currentGroup;
    private MutableLiveData<List<IDebtData>> currentGroupDebtsData;
    private MutableLiveData<BigDecimal> currentGroupBalance;

    public DetailedGroupViewModel() {
        modelEngine = ModelEngine.getInstance();
        EventBus.getInstance().register(this, EventBus.EVENT.SPECIFIC_GROUP_EVENT);
    }

    public IUserData getLoggedInUser() {
        return modelEngine.getLoggedInUser();
    }

    public LiveData<IGroupData> getCurrentGroup() {
        if (currentGroup == null) {
            currentGroup = new MutableLiveData<>();
        }
        return currentGroup;
    }

    public void setCurrentGroup(String groupID) throws Exception {
        if (currentGroup == null) {
            currentGroup = new MutableLiveData<>();
        }
        currentGroup.setValue(modelEngine.getGroup(groupID));

        // after the group is set, initialize the group components and fill them with data
        setCurrentGroupBalance();
        setCurrentGroupDebtsData();
    }

    public LiveData<List<IDebtData>> getCurrentGroupDebtsData() {
        if (currentGroupDebtsData == null) {
            currentGroupDebtsData = new MutableLiveData<>();
        }
        return currentGroupDebtsData;
    }

    public LiveData<BigDecimal> getCurrentGroupBalance() {
        if (currentGroupBalance == null) {
            currentGroupBalance = new MutableLiveData<>();
        }
        return currentGroupBalance;
    }

    public List<IDebtData> getCurrentGroupDebts(String groupID) {
        return currentGroupDebtsData.getValue();
    }

    /**
     * {Contact} \ {CurrentGroupMembers}
     *
     * @return Addable members to the group.
     */
    public List<IUserData> getAddableUsers() {
        List<IUserData> addableUsers = new ArrayList<>();
        for (IUserData contact : modelEngine.getContacts()) {
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
     * Adds the selected users from PickUserFragment to the current group in model.
     *
     * @return False if usersToBeAdded has yet to be set or is empty.
     */
    public boolean addSelectedMembersToCurrentGroup(Set<IUserData> users) {
        if (users == null || users.isEmpty()) {
            return false;
        }
        for (IUserData user : users) {
            if (!currentGroup.getValue().getIUserDataSet().contains(user)) {
                try {
                    modelEngine.addUserToGroup(user.getPhoneNumber(), currentGroup.getValue().getGroupID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * Leaves the current group.
     */
    public void leaveCurrentGroup() {
        try {
            modelEngine.leaveGroup(currentGroup.getValue().getGroupID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates data from database
     */
    public void updateDataFromDatabase() {
        try {
            modelEngine.refreshWithDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onModelEvent() {
        try {
            setCurrentGroup(currentGroup.getValue().getGroupID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                    getUserTotal(modelEngine.getLoggedInUser().getPhoneNumber()).toString()));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
