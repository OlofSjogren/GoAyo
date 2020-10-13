package com.goayo.debtify.viewmodel;

import com.goayo.debtify.model.RegistrationException;

import java.net.ConnectException;

/**
 * @Author Oscar Sanner and Olof Sjögren.
 * @date 2020-09-30
 * <p>
 * View model class responsible for handling registration request from Views and calling upon ModelEngine accordingly.
 */
public class SignUpViewModel extends ModelEngineViewModel {

    /**
     * Method called upon by views for registering a user with the input data from user.
     *
     * @param phoneNumber registration phone number
     * @param name        registration name
     * @param password    registration password
     * @return true if registration was successful, false if registration failed.
     */
    public boolean registerUser(String phoneNumber, String name, String password) {
        try {
            getModel().registerUser(phoneNumber, name, password);
        } catch (RegistrationException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        return true;
    }

}
