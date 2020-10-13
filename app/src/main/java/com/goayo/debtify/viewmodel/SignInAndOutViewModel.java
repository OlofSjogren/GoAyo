package com.goayo.debtify.viewmodel;

import android.util.Log;

/**
 * @Author Oscar Sanner and Olof Sjögren
 * @date 2020-09-30
 * <p>
 * View model class responsible for handling logIn request from Views and calling upon ModelEngine accordingly.
 */
public class SignInAndOutViewModel extends ModelEngineViewModel {

    /**
     * Method called upon by views for logging in a user with the input data from user.
     *
     * @param phoneNumber login phone number.
     * @param password    login password
     * @return true if login was successful, false if login failed.
     */
    public boolean logInUser(String phoneNumber, String password) {
        try {
            getModel().logInUser(phoneNumber, password);
        } catch (Exception e) {
            Log.d(e.getMessage(), "logInUser: ");
            return false;
        }
        return true;
    }

    /**
     * Method for logging out a logged in user in the model.
     */
    public void logOutUser() {
        getModel().logOutUser();
    }
}
