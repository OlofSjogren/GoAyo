package com.goayo.debtify;

/**
 * @author Olof Sjögren, Oscar Sanner
 * @date 2020-09-30
 * <p>
 * Observable interface for observer pattern.
 */
public interface IObservable {
    /**
     * Method for adding an observer to the observable.
     * @param observer the observer which will be added to the listening observers.
     */

    void addDetailedObserver(IObserver observer);

    void addContactsObserver(IObserver observer);

    /**
     * Method for removing an observer from the observable.
     * @param observer the observer which will be removed and stop listening to the observable.
     */
    void removeObserver(IObserver observer);



    /**
     * The method which will in some way notify the listening observers to update themselves accordingly.
     */
    void notifyAllObservers();
}
