package com.goayo.debtify.viewmodel;

import android.util.Log;

/**
 * @author Oscar Sanner and Olof Sjögren
 * @date 2020-09-30
 * <p>
 * View model class responsible for handling logIn request from Views and calling upon ModelEngine accordingly.
 * <p>
 * 2020-10-14 Modified by Alex Phu: Implemented getCurrentLoggedInUsersName()
 * 2020-10-14 Modified by Yenan Wang: Changed super class to ModelEngineViewModel
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
     * Gets the current logged in user's name
     *
     * @return string of user's name.
     */
    public String getCurrentLoggedInUsersName() {
        return getModel().getLoggedInUser().getName();
    }

    /**
     * Method for logging out a logged in user in the model.
     */
    public void logOutUser() {
        getModel().logOutUser();
    }
}
