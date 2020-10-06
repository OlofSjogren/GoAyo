package com.goayo.debtify;


public interface IContactsObservable {
    void addContactsObserver(IObserver observer);

    void removeContactsObserver(IObserver observer);

    void notifyContactsObserver(IObserver observer);
}
