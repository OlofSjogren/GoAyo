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
 * <p>
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
     *
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

    //TODO
    // * Have to compare number since objects are not same when comparing ACTUAL identical objects.
    // How come User.java overwritten equals() isn't working as intended?
    public void setPotentialUsersData() {
        if (potentialUsersToBeAddedData == null) {
            potentialUsersToBeAddedData = new MutableLiveData<List<IUserData>>();
        }
        List<IUserData> temporaryUserList = new ArrayList<>();

        //Temporary solution as equals doesn't work.
        List<String> phoneNumbers = new ArrayList<>();
        for (IUserData currentMembers : currentGroup.getValue().getIUserDataSet()) {
            phoneNumbers.add(currentMembers.getPhoneNumber());
        }

        for (IUserData contacts : modelEngine.getContacts()) {
            if (!phoneNumbers.contains(contacts.getPhoneNumber())) {
                temporaryUserList.add(contacts);
            }
        }

        //Use below when Equal in User works
        /*for (IUserData u : modelEngine.getContacts()) {
            if (!currentGroup.getValue().getIUserDataSet().contains(u)) {
                temporaryUserList.add(u);
            }
        }*/
        potentialUsersToBeAddedData.setValue(temporaryUserList);
    }

    public LiveData<List<IUserData>> getPotentialUsersData() {
        if (potentialUsersToBeAddedData == null) {
            Log.d("PickUserViewModel", "getPotentialUsersData: Returnes null");
            return null;
        }
        return potentialUsersToBeAddedData;
    }

    /**
     * Adds a user to selectedUsersData if it doesn't contain it, otherwise remove.
     * Called from CardViews in PickUserAdapter
     *
     * @param user User to be added
     */
    public void setSelectedUsersData(IUserData user) {
        if (selectedUsersData == null) {
            selectedUsersData = new MutableLiveData<List<IUserData>>();
            selectedUsersData.setValue(new ArrayList<IUserData>());
        }
        List<IUserData> temporaryUserList = selectedUsersData.getValue();

        if (!temporaryUserList.contains(user)) {
            temporaryUserList.add(user);
        } else {
            //Unselect user
            temporaryUserList.remove(user);
        }
        selectedUsersData.setValue(temporaryUserList);
    }

    public LiveData<List<IUserData>> getSelectedUsersData() {
        if (selectedUsersData == null) {
            Log.d("PickUserViewModel", "getSelectedUsersData: Returns null");
            return null;
        }
        return selectedUsersData;
    }

    public void setUsersToBeRemoved() {
        //TODO ("FUTURE USER STORY")
    }
}
