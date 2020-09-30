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
 * 2020-09-30 Modified by Alex Phu and Yenan: Cleaned up MyGroupsViewModel so it is only responsible for
 * fetching groupsdata.
 */

public class MyGroupsViewModel extends ViewModel {
    private MutableLiveData<Set<IGroupData>> groupsData;

    public MyGroupsViewModel() {
        super();
        Set<IGroupData> groupsData = ModelEngine.getInstance().getGroups();
        this.groupsData = new MutableLiveData<>(groupsData);
    }

    public LiveData<Set<IGroupData>> getGroupsData() {
        return groupsData;
    }
}
