package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.IUserData;
import com.goayo.debtify.model.UserAlreadyExistsException;
import com.goayo.debtify.model.UserNotFoundException;

import java.net.ConnectException;

/**
 * @author Yenan Wang
 * @date 2020-10-08
 * <p>
 * ViewModel class for AddContactFragment
 * <p>
 * 2020-10-14 Modified by Yenan Wang: Changed super class to ModelEngineViewModel
 * 2020-10-16 Modified by Yenan Wang: Added JavaDoc for all public methods
 */
public class AddContactViewModel extends ModelEngineViewModel {

    private MutableLiveData<IUserData> userToAdd;

    /**
     * Get the LiveData object so any views that uses this method may observe the LiveData
     * and update themselves accordingly
     *
     * @return LiveData of the IUserData to be added as a contact
     */
    public LiveData<IUserData> getUserData() {
        if (userToAdd == null) {
            userToAdd = new MutableLiveData<>();
        }
        return userToAdd;
    }

    /**
     * Set an IUserData to userToAdd and notifies everyone that observes this LiveData of the update
     *
     * @param user The selected IUserData to add to contacts
     */
    public void setUserData(IUserData user) {
        userToAdd.setValue(user);
    }

    /**
     * Retrieve an IUserData from a given phone number
     *
     * @param phoneNumber The phone number that belongs to a user
     * @return The user with the given phone number
     * @throws UserNotFoundException When the given phone number doesn't match anyone in the database
     * @throws ConnectException      When any kind of connection problem occurs
     */
    public IUserData findUser(String phoneNumber)
            throws UserNotFoundException, ConnectException {
        return getModel().getSingleUserFromDatabase(phoneNumber);
    }

    /**
     * Add the user that matches the given phone number to the contact list
     *
     * @param phoneNumber The phone number that belongs to a user
     * @throws UserNotFoundException      When the given phone number doesn't match anyone in the database
     * @throws UserAlreadyExistsException When the user that matches the phone number already
     *                                    exists as a contact or is the logged-in user
     * @throws ConnectException           When any kind of connection problem occurs
     */
    public void addUserToContacts(String phoneNumber)
            throws UserNotFoundException, UserAlreadyExistsException, ConnectException {
        getModel().addContact(phoneNumber);
    }
}
