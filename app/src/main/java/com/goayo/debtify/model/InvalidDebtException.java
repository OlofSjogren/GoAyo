package com.goayo.debtify.model;

/**
 * @author Olof Sjögren
 * @date 2020-09-22
 * <p>
 * Exception for when a Debt is invalid.
 */
public class InvalidDebtException extends Exception {

    /**
     * InvalidDebtException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public InvalidDebtException(String message) {
        super(message);
    }
}
