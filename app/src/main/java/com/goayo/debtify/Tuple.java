package com.goayo.debtify;

/**
 * Simple tuple class for holding two element for when the elements are related to each other.
 *
 * @param <T> First type of object.
 * @param <U> Second type of object.
 */

public class Tuple<T, U> {

    private final T first;
    private final U second;

    public Tuple(T element1, U second) {
        this.first = element1;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}