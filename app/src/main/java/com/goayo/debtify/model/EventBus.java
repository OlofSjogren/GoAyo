package com.goayo.debtify.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Oscar Sanner and Olof Sjögren
 * @date 2020-10-07
 * <p>
 * 2020-10-13 Modified by Olof Sjögren: Now utilizes enum-types instead of class-types.
 * 2020-10-14 Modified by Olof Sjögren: Added JDocs.
 * 2020-10-15 Modified by Oscar Sanner and Olof Sjögren: Class now throws appropriate exceptions.
 * 2020-10-19 Modified by Oscar Sanner and Olof Sjögren: Refactored singleton method to use
 * enum instead of the traditional static instance.
 */
public enum EventBus {

    INSTANCE;

    /**
     * All the different types of events which can be published.
     */
    public enum EVENT {
        /**
         * Event associated with the user contacts.
         */
        CONTACT_EVENT,

        /**
         * Event associated with the user's groups.
         */
        GROUPS_EVENT,

        /**
         * Event associated with a specific group.
         */
        SPECIFIC_GROUP_EVENT,
    }

    /**
     * Map where an EVENT acts as a key to a corresponding list of IEventHandlers which are to be notified when an event of key's type is published.
     */
    private final Map<EVENT, List<IEventHandler>> listenerMap = new HashMap<>();

    /**
     * Method for registering an IEventHandler to be notified when a specific event is published.
     *
     * @param handler   the IEventHandler who wishes to be notified of a specific event.
     * @param eventType the type of event publications the handler wishes to be notified of.
     */
    public void register(IEventHandler handler, EVENT eventType) {
        if (listenerMap.get(eventType) == null) {
            listenerMap.put(eventType, new ArrayList<>());
        }
        listenerMap.get(eventType).add(handler);
    }

    /**
     * Method for unregistering a handler from a specific event publication it is registered to.
     *
     * @param handler   the handler who wishes to unregister.
     * @param eventType the type of event publication the handler wants to unregister from.
     */
    public void unRegister(IEventHandler handler, EVENT eventType) {
        listenerMap.get(eventType).remove(handler);
    }

    /**
     * Method for publishing an event and thus notifying all IEventHandlers registered to the publication of the that event to be notified.
     *
     * @param eventType the type of event to publish.
     */
    public void publish(EVENT eventType) {
        List<IEventHandler> eventHandlerList = listenerMap.get(eventType);
        if (eventHandlerList == null) {
            return;
        }
        for (IEventHandler handler : eventHandlerList) {
            handler.onModelEvent();
        }
    }
}
