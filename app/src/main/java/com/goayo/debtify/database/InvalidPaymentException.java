package com.goayo.debtify.database;

/**
 * @author Olof Sjögren
 * @date 2020-09-22
 * <p>
 * Exception for when a payment is invalid.
 **/

public class InvalidPaymentException extends Exception {

    /**
     * InvalidPaymentException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public InvalidPaymentException(String message) {
        super(message);
    }
}
