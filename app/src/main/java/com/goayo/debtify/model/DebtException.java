package com.goayo.debtify.model;

/**
 * @author Yenan Wang
 * @date 2020-10-12
 * <p>
 * Exception related to debt/payment creation
 */
public class DebtException extends Exception {

    /**
     * create a new exception with a given error message
     *
     * @param message message to be sent to the users
     */
    public DebtException(String message) {
        super(message);
    }
}
