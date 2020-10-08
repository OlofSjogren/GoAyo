package com.goayo.debtify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Gabriel Brattgård
 * @date 2020-09-28
 * <p>
 * View model class responsible for data flow from the model to all views related
 * to Contacts.
 * <p>
 * 2020-10-08 Modified by Yenan: refactor to interact with the ModelEngine
 */
public class ContactsViewModel extends ViewModel {
    private MutableLiveData<List<IUserData>> contactsData;
    private ModelEngine modelEngine;

    public ContactsViewModel() {
        super();
        modelEngine = ModelEngine.getInstance();
    }

    public LiveData<List<IUserData>> getContactsData() {
        if (contactsData == null) {
            contactsData = new MutableLiveData<List<IUserData>>(new ArrayList<IUserData>());
            setContactsData(new ArrayList<>(modelEngine.getContacts()));
        }
        return contactsData;
    }

    public void setContactsData(List<IUserData> contactsData) {
        this.contactsData.setValue(contactsData);
    }
}