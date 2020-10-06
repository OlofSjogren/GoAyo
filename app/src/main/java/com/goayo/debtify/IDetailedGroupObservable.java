package com.goayo.debtify;


public interface IDetailedGroupObservable {
    void addDetailedGroupObserver(IObserver observer);

    void removeDetailedGroupObserver(IObserver observer);

    void notifyDetailedGroupObserver(IObserver observer);
}
