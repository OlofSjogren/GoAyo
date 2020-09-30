package com.goayo.debtify.viewModel;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.goayo.debtify.model.ModelEngine;

/**
 * View model class responsible for handling registration request from Views and calling upon ModelEngine accordingly.
 */
public class SignUpViewModel extends ViewModel {

    /**
     * Method called upon by views for registering a user with the input data from user.
     * @param phoneNumber registration phone number
     * @param name registration name
     * @param password registration password
     * @return true if registration was successful, false if registration failed.
     */
    public boolean registerUser(String phoneNumber, String name, String password){
        try {
            ModelEngine.getInstance().registerUser(phoneNumber, name, password);
        } catch (Exception e) {
            Log.d(e.getMessage(), "registerUser: ");
            return false;
        }
        return true;
    }
    
}
