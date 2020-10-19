package com.goayo.debtify.viewmodel;

import com.goayo.debtify.model.IUserData;
import com.goayo.debtify.model.RegistrationException;
import com.goayo.debtify.model.UserNotFoundException;

import java.net.ConnectException;
import java.util.ArrayList;
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
     * @throws RegistrationException thrown if the registration fails in the database.
     * @throws ConnectException      thrown if unable to connect to the database.
     * @throws UserNotFoundException if a group is created by the logged in user, but all of the members are not in the contact list of said user.
     */
    public void createGroup(String groupName, Set<IUserData> users)
            throws UserNotFoundException, RegistrationException, ConnectException {
        getModel().createGroup(groupName, ViewModelUtil.convertToUserPhoneNumberSet(users));
    }

}
