package com.goayo.debtify.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Oscar Sanner and Olof Sjögren
 * @date 2020-10-07
 *
 * 2020-10-13 Modified by Olof Sjögren: Now utilizes enum-types instead of class-types.
 */
public class EventBus {

    public enum EVENT {
        CONTACT_EVENT,
        GROUPS_EVENT,
        SPECIFIC_GROUP_EVENT,
    }

    private static EventBus instance;
    private Map<EVENT, List<IEventHandler>> listenerMap;

    private EventBus() {
        listenerMap = new HashMap<>();
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void register(IEventHandler handler, EVENT eventType) {
        if (listenerMap.get(eventType) == null) {
            listenerMap.put(eventType, new ArrayList<>());
        }
        listenerMap.get(eventType).add(handler);
    }

    public void unRegister(IEventHandler handler, EVENT eventType) {
        listenerMap.get(eventType).remove(handler);
        //TODO: Handle null exception perhaps?
    }

    public void publish(EVENT eventType) {
        List<IEventHandler> eventHandlerList = listenerMap.get(eventType);
        if (eventHandlerList == null) {
            return;
            //Todo; Maybe something else?
        }
        for (IEventHandler handler : eventHandlerList) {
            handler.onModelEvent();
        }
    }
}
