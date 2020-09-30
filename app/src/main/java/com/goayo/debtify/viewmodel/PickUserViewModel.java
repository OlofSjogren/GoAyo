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
import java.util.Objects;
import java.util.Set;

/**
 * @author Alex Phu
 * @date 2020-09-29
 * <p>
 * ViewModel for PickUserFragment, and PickUserAdapter.
 */
public class PickUserViewModel extends ViewModel {
    private final ModelEngine modelEngine;
    private MutableLiveData<List<IUserData>> initialUsers;
    private MutableLiveData<List<IUserData>> selectedUsersData;
    private MutableLiveData<Boolean> isMultipleChoice;

    /**
     * Constructor for PickUserViewModel
     */
    public PickUserViewModel() {
        super();
        modelEngine = ModelEngine.getInstance();
        initialUsers = new MutableLiveData<>();
        initialUsers.setValue(new ArrayList<IUserData>());
        selectedUsersData = new MutableLiveData<>();
        selectedUsersData.setValue(new ArrayList<IUserData>());
        isMultipleChoice = new MutableLiveData<>(true);
    }

    public List<IUserData> getInitialUsers() {
        return initialUsers.getValue();
    }

    public void setInitialUsers(List<IUserData> initialUsers) {
        this.initialUsers.setValue(initialUsers);
    }

    public LiveData<List<IUserData>> getSelectedUsersData() {
        return selectedUsersData;
    }

    public void setSelectedUsersData(List<IUserData> selectedUsersData) {
        this.selectedUsersData.setValue(selectedUsersData);
    }

    public boolean getIsMultipleChoice() {
        return isMultipleChoice.getValue();
    }

    public void setIsMultipleChoice(boolean isMultipleChoice) {
        this.isMultipleChoice.setValue(isMultipleChoice);
    }
}
