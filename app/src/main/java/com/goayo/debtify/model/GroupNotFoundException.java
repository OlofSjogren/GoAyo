package com.goayo.debtify.model;

/**
 * @author Olof Sjögren
 * @date 2020-09-22
 * <p>
 * Exception for instances where the a specific Group can't be found.
 */
public class GroupNotFoundException extends Exception {

    /**
     * GroupNotFoundException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public GroupNotFoundException(String message) {
        super(message);
    }
}
