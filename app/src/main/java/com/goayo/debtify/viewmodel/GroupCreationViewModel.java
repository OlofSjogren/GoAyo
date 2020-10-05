package com.goayo.debtify.viewmodel;

import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.List;

public class GroupCreationViewModel extends ViewModel {

    private ModelEngine model;

    public GroupCreationViewModel() {
        model = ModelEngine.getInstance();
    }

    public List<IUserData> getContacts() {
        return new ArrayList<>(model.getContacts());
    }
}
