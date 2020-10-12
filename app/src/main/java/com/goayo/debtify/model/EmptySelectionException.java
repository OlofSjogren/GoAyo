package com.goayo.debtify.model;

/**
 * @author Olof Sjögren
 * @date 2020-09-22
 * <p>
 * Exception related to empty selections.
 *
 * 2020-10-08 Modified by Oscar Sanner and Olof Sjögren: Made exception a runtime exception
 **/

public class EmptySelectionException extends RuntimeException {

    /**
     * EmptySelectionException constructor with message describing the exceptions reason.
     *
     * @param message description of why the exception is thrown.
     */
    public EmptySelectionException(String message) {
        super(message);
    }
}
