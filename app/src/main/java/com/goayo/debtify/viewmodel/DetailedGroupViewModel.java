package com.goayo.debtify.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.List;

public class DetailedGroupViewModel extends ViewModel {
    private MutableLiveData<IGroupData> currentGroup;
    private final ModelEngine modelEngine;

    public DetailedGroupViewModel() {
        modelEngine = ModelEngine.getInstance();
    }

    public MutableLiveData<IGroupData> getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(String groupID) {
        if(currentGroup == null){
            this.currentGroup = new MutableLiveData<>();
        }
        try {
            currentGroup.setValue(modelEngine.getGroup(groupID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the selected users from PickUserFragment to the current group.
     * @return False if usersToBeAdded has yet to be set or is empty.
     */
    public boolean addSelectedMembersToCurrentGroup(List<IUserData> users) {
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
