package com.goayo.debtify;

/**
 * @author Olof Sjögren, Oscar Sanner
 * @date 2020-09-15
 * <p>
 * Observer interface for observer pattern.
 */
public interface IObserver {
    /**
     * Method called on by observable which notifies all the listening observers to update.
     */
    void update();
}
