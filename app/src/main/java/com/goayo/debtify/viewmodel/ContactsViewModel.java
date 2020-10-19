package com.goayo.debtify.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.goayo.debtify.model.EventBus;
import com.goayo.debtify.model.IEventHandler;
import com.goayo.debtify.model.IUserData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Brattgård
 * @date 2020-09-28
 * <p>
 * View model class responsible for data flow from the model to all views related
 * to Contacts.
 * <p>
 * 2020-10-08 Modified by Yenan Wang: refactor to interact with the ModelEngine, add removeContacts(...) method
 * 2020-10-09 Modified by Yenan Wang & Alex Phu: connect with EventBus so the LiveDatas updates immediately after modification
 * 2020-10-14 Modified by Yenan Wang: Changed super class to ModelEngineViewModel
 * 2020-10-16 Modified by Yenan Wang: Updated JavaDoc for all public methods
 */
public class ContactsViewModel extends ModelEngineViewModel implements IEventHandler {

    private MutableLiveData<List<IUserData>> contactsData;

    /**
     * Subscribe itself to the CONTACT_EVENT so that it can be notifies of all updates to
     * that specific event immediately
     */
    public ContactsViewModel() {
        super();
        EventBus.getInstance().register(this, EventBus.EVENT.CONTACT_EVENT);
    }

    /**
     * Get the LiveData object so any views that uses this method may observe the LiveData
     * and update themselves accordingly
     *
     * @return The LiveData object representing the contacts
     */
    public LiveData<List<IUserData>> getContactsData() {
        if (contactsData == null) {
            contactsData = new MutableLiveData<>();
            setContactsData(new ArrayList<>(getModel().getContacts()));
        }
        return contactsData;
    }

    /**
     * Set a List of IUserData to contactsData and notifies everyone that observes this
     * LiveData of the update
     *
     * @param contactsData The contacts that replaces the current contacts
     */
    public void setContactsData(List<IUserData> contactsData) {
        this.contactsData.setValue(contactsData);
    }

    /**
     * Remove a list of user from contacts in the model
     *
     * @param contactsToRemove The contacts to be removed from the logged in user
     * @throws Exception e
     */
    public void removeContacts(List<IUserData> contactsToRemove)
    // TODO SPECIFY IT!!!!
            throws Exception {
        for (IUserData user : contactsToRemove) {
            getModel().removeContact(user.getPhoneNumber());
        }
    }

    /**
     * Retrieve contacts data again from the model whenever there is an update
     */
    @Override
    public void onModelEvent() {
        updateContactsData();
    }

    private void updateContactsData() {
        setContactsData(new ArrayList<>(getModel().getContacts()));
    }
}