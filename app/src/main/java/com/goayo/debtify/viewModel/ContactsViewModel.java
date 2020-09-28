package com.goayo.debtify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.Set;

/**
 * View model class responsible for data flow from the model to all views related
 * to Contacts.
 *
 * @author Gabriel Brattgård
 * @date 2020-09-28
 */

public class ContactsViewModel extends ViewModel {
    private MutableLiveData<Set<IUserData>> contactsData;

    public LiveData<Set<IUserData>> getContactsData() {
        if (contactsData == null) {
            contactsData = new MutableLiveData<>();
            Set<IUserData> dataFromModel;
            dataFromModel = ModelEngine.getInstance().getContacts();
            contactsData.setValue(dataFromModel);
        }
        return contactsData;
    }

    public void setContactsData(Set<IUserData> contactsData) {
        this.contactsData.setValue(contactsData);
    }
}