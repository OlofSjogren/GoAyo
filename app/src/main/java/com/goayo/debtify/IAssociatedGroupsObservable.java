package com.goayo.debtify;


import com.goayo.debtify.IObserver;

public interface IAssociatedGroupsObservable {
    void addAssociatedGroupsObserver(IObserver observer);

    void removeAssociatedGroupsObserver(IObserver observer);

    void notifyAssociatedGroupsObserver(IObserver observer);
}
