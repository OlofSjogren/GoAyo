package com.goayo.debtify.model;

/**
 * @Author Oscar Sanner and Olof Sjögren
 * @date 2020-10-07
 * <p>
 * Interface for event handlers who wish to subscribe to the EventBus and it's events.
 * <p>
 * 2020-10-13 Modified by Olof Sjögren: removed event parameter from method. Is now only a notification.
 */

public interface IEventHandler {

    /**
     * Notification method used to update the handler when a corresponding event it is listening to is fired.
     */
    void onModelEvent();
}