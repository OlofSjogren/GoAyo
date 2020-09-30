package com.goayo.debtify.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DetailedGroupViewModel extends ViewModel {
    private MutableLiveData<IGroupData> currentGroup;
    private final ModelEngine modelEngine;

    public DetailedGroupViewModel() {
        modelEngine = ModelEngine.getInstance();
    }

    public IUserData getLoggedInUser() {
        return modelEngine.getLoggedInUser();
    }

    public MutableLiveData<IGroupData> getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(String groupID) {
        if (currentGroup == null) {
            this.currentGroup = new MutableLiveData<>();
        }
        try {
            currentGroup.setValue(modelEngine.getGroup(groupID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {Contact} \ {CurrentGroupMembers}
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

}
