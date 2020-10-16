package com.goayo.debtify.viewmodel;

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
 * <p>
 * 2020-10-14 Modified by Yenan Wang: Changed super class to ModelEngineViewModel
 * 2020-10-16 Modified by Yenan Wang: Updated JavaDoc for all public methods
 */
public class GroupCreationViewModel extends ModelEngineViewModel {

    /**
     * Retrieve the contacts of the logged-in user
     *
     * @return The List of contacts that belongs to the logged-in user
     */
    public List<IUserData> getContacts() {
        return new ArrayList<>(getModel().getContacts());
    }

    /**
     * Create a group given a name and a Set of initial users to be added into the group
     *
     * @param groupName The name of the group to be created
     * @param users The users to be added into the group to be created
     * @throws Exception
     */
    public void createGroup(String groupName, Set<IUserData> users)
    // TODO SPECIFY IT!!!!
            throws Exception {
        getModel().createGroup(groupName, convertToString(users));
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
