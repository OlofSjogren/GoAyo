package com.goayo.debtify.database;

/**
 * @author Olof Sjögren
 * @date 2020-09-22
 * <p>
 * Exception related to empty selections.
 **/

public class EmptySelectionException extends Exception {

    /**
     * EmptySelectionException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public EmptySelectionException(String message) {
        super(message);
    }
}
