package com.goayo.debtify.model;

/**
 * @author Oscar Sanner and Olof Sjögren
 * @date 2020-10-08
 * <p>
 * Exception for when the user is not logged in, applicable to for instance Session class.
 **/

public class UserNotLoggedInException extends RuntimeException {

    /**
     * UserNotLoggedInException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
