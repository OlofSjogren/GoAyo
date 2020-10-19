package com.goayo.debtify.viewmodel;

import com.goayo.debtify.model.IUserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Yenan Wang, Alex Phu
 * @date 2020-10-05
 * <p>
 * ViewModel for GroupCreationActivity
 */
public class GroupCreationViewModel extends ModelEngineViewModel {

    public List<IUserData> getContacts() {
        return new ArrayList<>(getModel().getContacts());
    }

    public void createGroup(String groupName, Set<IUserData> users) throws Exception {
        getModel().createGroup(groupName, ViewModelUtil.convertToUserPhoneNumberSet(users));
    }

}
