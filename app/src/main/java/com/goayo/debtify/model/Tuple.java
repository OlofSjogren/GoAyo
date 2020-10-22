package com.goayo.debtify.model;

/**
 * @author Oscar Sanner
 * @date 2020-10-12
 * <p>
 * Simple tuple class for holding two element for when the elements are related to each other.
 * <p>
 * @param <T> First type of object.
 * @param <U> Second type of object.
 * <p>
 * 2020-10-13 Modified by Olof Sjögren: Minor change of parameter name for readability as well as JDOCs.
 */
public class Tuple<T, U> {

    private final T first;
    private final U second;

    public Tuple(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}