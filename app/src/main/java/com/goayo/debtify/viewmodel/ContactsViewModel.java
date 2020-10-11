package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Brattgård
 * @date 2020-09-28
 * <p>
 * View model class responsible for data flow from the model to all views related
 * to Contacts.
 * <p>
 * 2020-10-08 Modified by Yenan: refactor to interact with the ModelEngine, add removeContacts(...) method
 */
public class ContactsViewModel extends ViewModel {
    private MutableLiveData<List<IUserData>> contactsData;
    private ModelEngine modelEngine;

    // TODO need to observe the model to update contacts

    public ContactsViewModel() {
        super();
        modelEngine = ModelEngine.getInstance();
    }

    public LiveData<List<IUserData>> getContactsData() {
        if (contactsData == null) {
            contactsData = new MutableLiveData<>();
            setContactsData(new ArrayList<>(modelEngine.getContacts()));
        }
        return contactsData;
    }

    public void setContactsData(List<IUserData> contactsData) {
        this.contactsData.setValue(contactsData);
    }

    /**
     * Remove a list of user from contacts and then update the LiveData
     *
     * @param contactsToRemove the contacts to be removed from the logged in user
     * @throws Exception e
     */
    public void removeContacts(List<IUserData> contactsToRemove) throws Exception {
        for (IUserData user : contactsToRemove) {
            modelEngine.removeContact(user.getPhoneNumber());
        }
        setContactsData(new ArrayList<>(modelEngine.getContacts()));
    }
}