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
 */
public class AddContactViewModel extends ModelEngineViewModel {

    private MutableLiveData<IUserData> userToAdd;

    public LiveData<IUserData> getUserData() {
        if (userToAdd == null) {
            userToAdd = new MutableLiveData<>();
        }
        return userToAdd;
    }

    public void setUserData(IUserData user) {
        userToAdd.setValue(user);
    }

    public IUserData findUser(String phoneNumber) throws UserNotFoundException, ConnectException {
        return getModel().getSingleUserFromDatabase(phoneNumber);
    }

    public void addUserToContacts(String phoneNumber) throws UserNotFoundException, UserAlreadyExistsException, ConnectException {
        getModel().addContact(phoneNumber);
    }
}
