package com.goayo.debtify.model;

/**
 * @author Olof Sjögren
 * @date 2020-09-22
 * <p>
 * Exception for when trying to add a User to a collection but it already exists.
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * UserAlreadyExistsException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
