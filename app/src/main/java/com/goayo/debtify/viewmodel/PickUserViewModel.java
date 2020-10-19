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
 * 2020-09-30 Modified by Alex Phu & Yenan Wang: Implemented the vast majority of functionality
 * 2020-10-14 Modified by Yenan Wang: Changed super class to ModelEngineViewModel
 * 2020-10-16 Modified by Yenan Wang: Updated JavaDoc for all public methods
 */
public class PickUserViewModel extends ModelEngineViewModel {
    private MutableLiveData<List<IUserData>> initialUsers;
    private MutableLiveData<List<IUserData>> selectedUsersData;
    private MutableLiveData<Boolean> isMultipleChoice;
    private MutableLiveData<Integer> destination;

    /**
     * Initiate all LiveData objects this ViewModel holds
     */
    public PickUserViewModel() {
        super();
        initialUsers = new MutableLiveData<>();
        initialUsers.setValue(new ArrayList<>());

        selectedUsersData = new MutableLiveData<>();
        selectedUsersData.setValue(new ArrayList<>());

        isMultipleChoice = new MutableLiveData<>(true);
        destination = new MutableLiveData<>();
    }

    /**
     * Retrieve a List of users to be displayed in PickUserFragment
     *
     * @return A List of users to be displayed in PickUserFragment
     */
    public List<IUserData> getInitialUsers() {
        return initialUsers.getValue();
    }

    /**
     * Set a List of IUserData to initialUsers so that the list may be displayed in PickUserFragment
     *
     * @param initialUsers The List of users to be set for display
     */
    public void setInitialUsers(List<IUserData> initialUsers) {
        this.initialUsers.setValue(initialUsers);
    }

    /**
     * Get the LiveData object so any views that uses this method may observe the LiveData
     * and update themselves accordingly
     *
     * @return the LiveData object representing the List of users selected in PickUserFragment
     */
    public LiveData<List<IUserData>> getSelectedUsersData() {
        return selectedUsersData;
    }

    /**
     * Set a List of users to selectedUserData and notifies everyone that observes this
     * LiveData of the update
     *
     * @param selectedUsersData The List of users that replaces the current List of selected users
     */
    public void setSelectedUsersData(List<IUserData> selectedUsersData) {
        this.selectedUsersData.setValue(selectedUsersData);
    }

    /**
     * Retrieve a boolean that will decide whether or not PickUserFragment will display the initial
     * users with radio buttons (single select) or checkboxes (multiple select)
     *
     * @return A boolean that decides whether or not PickUserFragment is multiple selectable
     */
    public boolean getIsMultipleChoice() {
        return isMultipleChoice.getValue();
    }

    /**
     * Set a boolean to isMultipleChoice so that its value may be retrieved by PickUserFragment
     * to determine whether or not it is multiple selectable
     *
     * @param isMultipleChoice A boolean that decides whether or not PickUserFragment is
     *                         multiple selectable
     */
    public void setIsMultipleChoice(boolean isMultipleChoice) {
        this.isMultipleChoice.setValue(isMultipleChoice);
    }

    /**
     * Set the destination as null
     */
    public void clearDestination() {
        destination.setValue(null);
    }

    /**
     * Retrieve the destination which PickUserFragment will navigate to once the users have
     * been selected
     *
     * @return The action of navigation from PickUserFragment to another destination
     */
    public Integer getDestination() {
        return destination.getValue();
    }

    /**
     * Set where PickUserFragment will navigate to once the users have been selected
     *
     * @param actionDestination The action of navigation from PickUserFragment to another destination
     */
    public void setDestination(int actionDestination) {
        destination.setValue(actionDestination);
    }
}
