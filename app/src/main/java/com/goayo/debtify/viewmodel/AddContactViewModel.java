package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.model.UserAlreadyExistsException;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.model.IUserData;

import java.net.ConnectException;

/**
 * @author Yenan Wang
 * @date 2020-10-08
 * <p>
 * ViewModel class for AddContactFragment
 */
public class AddContactViewModel extends ViewModel {

    private MutableLiveData<IUserData> userToAdd;
    private ModelEngine modelEngine;

    public AddContactViewModel() {
        modelEngine = ModelEngine.getInstance();
    }

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
        return modelEngine.getSingleUserFromDatabase(phoneNumber);
    }

    public void addUserToContacts(String phoneNumber) throws UserNotFoundException, UserAlreadyExistsException, ConnectException {
        modelEngine.addContact(phoneNumber);
    }
}
