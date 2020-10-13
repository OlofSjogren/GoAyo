package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.IUserData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Phu
 * @date 2020-09-29
 * <p>
 * ViewModel for PickUserFragment, and PickUserAdapter.
 * <p>
 * 2020-09-30 Modified by Alex and Yenan: Implemented
 */
public class PickUserViewModel extends ModelEngineViewModel {
    private MutableLiveData<List<IUserData>> initialUsers;
    private MutableLiveData<List<IUserData>> selectedUsersData;
    private MutableLiveData<Boolean> isMultipleChoice;
    private MutableLiveData<Integer> destination;

    /**
     * Constructor for PickUserViewModel
     */
    public PickUserViewModel() {
        super();
        initialUsers = new MutableLiveData<>();
        initialUsers.setValue(new ArrayList<IUserData>());
        selectedUsersData = new MutableLiveData<>();
        selectedUsersData.setValue(new ArrayList<IUserData>());
        isMultipleChoice = new MutableLiveData<>(true);
        destination = new MutableLiveData<>();
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

    public void clearDestination() {
        destination.setValue(null);
    }

    public Integer getDestination() {
        return destination.getValue();
    }

    public void setDestination(int actionDestination) {
        destination.setValue(actionDestination);
    }
}
