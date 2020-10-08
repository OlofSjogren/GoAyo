package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IGroupData;

import java.util.Set;

/**
 * View model class responsible for getting group data from the model and
 * handing it up to the views.
 *
 * @author Oscar Sanner and Alex Phu
 * @date 2020-09-25
 * <p>
 * 2020-09-30 Modified by Alex Phu and Yenan Wang: Cleaned up MyGroupsViewModel so it is only responsible for
 * fetching groupsdata.
 *
 * 2020-10-08 Modified by Alex Phu: Added getCurrentLoggedInUsersPhoneNumber() for GroupViewAdapter. To be able to get
 * UserTotal in each group.
 */

public class MyGroupsViewModel extends ViewModel {
    private MutableLiveData<Set<IGroupData>> groupsData;
    private ModelEngine modelEngine;

    public MyGroupsViewModel() {
        super();
        modelEngine = ModelEngine.getInstance();
        Set<IGroupData> groupsData = modelEngine.getGroups();
        this.groupsData = new MutableLiveData<>(groupsData);
    }

    public LiveData<Set<IGroupData>> getGroupsData() {
        return groupsData;
    }

    public String getCurrentLoggedInUsersPhoneNumber() {
        return modelEngine.getLoggedInUser().getPhoneNumber();
    }
}
