package com.goayo.debtify.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Phu
 * @date 2020-09-29
 * <p>
 * ViewModel for PickUserFragment, and PickUserAdapter.
 */
public class PickUserViewModel extends ViewModel {
    private final ModelEngine modelEngine;
    private MutableLiveData<IGroupData> currentGroup;
    private MutableLiveData<List<IUserData>> initialUsers;
    private MutableLiveData<Set<IUserData>> selectedUsersData;

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
        }
    }

    //TODO
    // * Have to compare number since objects are not same when comparing ACTUAL identical objects.
    // How come User.java overwritten equals() isn't working as intended?

    /**
     * Sets initialUsers with only "addable" users. (DetailedGroupActivity)
     */
    public void setInitialUsersToExcludeCurrentGroupMembers() {
        //Users to be displayed at beginning
        if (initialUsers == null) {
            initialUsers = new MutableLiveData<List<IUserData>>();
        }
        List<IUserData> temporaryUserList = new ArrayList<>();

        //Temporary solution as equals doesn't work.
        List<String> phoneNumbers = new ArrayList<>();
        for (IUserData currentMembers : currentGroup.getValue().getIUserDataSet()) {
            //Phone number of each current member
            phoneNumbers.add(currentMembers.getPhoneNumber());
        }

        for (IUserData contacts : modelEngine.getContacts()) {
            //If phone number doesn't match with contacts, add
            //Only show users who aren't in the group.
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
        initialUsers.setValue(temporaryUserList);
    }

    /**
     * Sets initialUsers with all group members. (DebtActivity)
     */
    public void setInitialUsersToAllGroupMembers() {
        if (initialUsers == null) {
            initialUsers = new MutableLiveData<List<IUserData>>();
        }
        List<IUserData> temporaryUserList = new ArrayList<>(currentGroup.getValue().getIUserDataSet());
        initialUsers.setValue(temporaryUserList);
    }

    public LiveData<List<IUserData>> getInitialUsers() {
        if (initialUsers == null) {
            Log.d("PickUserViewModel", "getPotentialUsersData: Returnes null");
            return null;
        }
        return initialUsers;
    }

    /**
     * Adds a user to selectedUsersData if it doesn't contain it, otherwise remove.
     *
     * @param user User to be added
     */
    public void setSelectedUsersData(IUserData user) {
        if (selectedUsersData == null) {
            selectedUsersData = new MutableLiveData<Set<IUserData>>();
            selectedUsersData.setValue(new HashSet<IUserData>());
        }
        Set<IUserData> temporaryUserList = selectedUsersData.getValue();

        if (!temporaryUserList.contains(user)) {
            temporaryUserList.add(user);
        } else {
            //Unselect user
            temporaryUserList.remove(user);
        }
        selectedUsersData.setValue(temporaryUserList);
    }

    public LiveData<Set<IUserData>> getSelectedUsersData() {
        if (selectedUsersData == null) {
            Log.d("PickUserViewModel", "getSelectedUsersData: Returns null");
            return null;
        }
        return selectedUsersData;
    }

    public void resetSelectedData(){
        selectedUsersData.setValue(new HashSet<IUserData>());
    }
}
