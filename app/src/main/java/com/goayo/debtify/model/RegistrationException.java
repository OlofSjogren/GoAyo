package com.goayo.debtify.model;

/**
 * @author Olof Sjögren
 * @date 2020-09-22
 * <p>
 * Exception for registration failures.
 **/

public class RegistrationException extends Exception {

    /**
     * RegistrationException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public RegistrationException(String message) {
        super(message);
    }
}
