package com.goayo.debtify.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.goayo.debtify.model.IEventHandler;
import com.goayo.debtify.model.IModelEvent;
import com.goayo.debtify.model.ModelEngine;

/**
 * @Author Oscar Sanner and Olof Sjögren
 * @date 2020-09-30
 *
 * View model class responsible for handling logIn request from Views and calling upon ModelEngine accordingly.
 */
public class SignInAndOutViewModel extends ViewModel {

    /**
     * Method called upon by views for logging in a user with the input data from user.
     * @param phoneNumber login phone number.
     * @param password login password
     * @return true if login was successful, false if login failed.
     */
    public boolean logInUser(String phoneNumber, String password){
        try {
            ModelEngine.getInstance().logInUser(phoneNumber, password);
        } catch (Exception e) {
            Log.d(e.getMessage(), "logInUser: ");
            return false;
        }
        return true;
    }

    /**
     * Method for logging out a logged in user in the model.
     */
    public void logOutUser(){
        ModelEngine.getInstance().logOutUser();
    }
}
