package com.goayo.debtify.database;

/**
 * @author Olof Sjögren
 * @date 2020-09-22
 * <p>
 * Exception related to failing to find user in a collection.
 **/

public class UserNotFoundException extends Exception {

    /**
     * UserNotFoundException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
