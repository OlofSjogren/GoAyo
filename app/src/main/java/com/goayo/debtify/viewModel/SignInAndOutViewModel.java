package com.goayo.debtify.viewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.goayo.debtify.IObserver;
import com.goayo.debtify.model.ModelEngine;

public class SignInAndOutViewModel extends ViewModel {

    public boolean logInUser(String phoneNumber, String password){
        try {
            ModelEngine.getInstance().logInUser(phoneNumber, password);
        } catch (Exception e) {
            Log.d(e.getMessage(), "logInUser: ");
            return false;
        }
        return true;
    }

    public void logOutUser(){
        ModelEngine.getInstance().logOutUser();
    }
}
