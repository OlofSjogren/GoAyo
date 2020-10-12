package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.model.UserAlreadyExistsException;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

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

    // TODO you should not have to go through every group to find a single user,
    //      need to implement this method in ModelEngine
    public IUserData findUser(String phoneNumber) throws UserNotFoundException {
        for (IGroupData group : modelEngine.getGroups()) {
            for (IUserData user : group.getIUserDataSet()) {
                if (user.getPhoneNumber().equals(phoneNumber)) {
                    return user;
                }
            }
        }
        throw new UserNotFoundException("No user with such phone number!");
    }

    public void addUserToContacts(String phoneNumber) throws UserNotFoundException, UserAlreadyExistsException, ConnectException {
        modelEngine.addContact(phoneNumber);
    }
}
