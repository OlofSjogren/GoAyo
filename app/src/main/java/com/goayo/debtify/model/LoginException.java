package com.goayo.debtify.model;

/**
 * @author Olof Sjögren
 * @date 2020-09-22
 * <p>
 * Exception for a login failure.
 */
public class LoginException extends Exception {

    /**
     * LoginException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public LoginException(String message) {
        super(message);
    }
}
