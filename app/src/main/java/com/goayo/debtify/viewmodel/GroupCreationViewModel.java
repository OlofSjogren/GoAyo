package com.goayo.debtify.viewmodel;

import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.model.IUserData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yenan Wang, Alex Phu
 * @date 2020-10-05
 * <p>
 * ViewModel for GroupCreationActivity
 */
public class GroupCreationViewModel extends ViewModel {

    private ModelEngine model;

    public GroupCreationViewModel() {
        model = ModelEngine.getInstance();
    }

    public List<IUserData> getContacts() {
        return new ArrayList<>(model.getContacts());
    }

    public void createGroup(String groupName, Set<IUserData> users) throws Exception {
        model.createGroup(groupName, convertToString(users));
    }

    // TODO this method shouldn't be needed
    private Set<String> convertToString(Set<IUserData> userDataSet) {
        Set<String> userToString = new HashSet<>();
        for (IUserData user : userDataSet) {
            userToString.add(user.getPhoneNumber());
        }
        return userToString;
    }
}
