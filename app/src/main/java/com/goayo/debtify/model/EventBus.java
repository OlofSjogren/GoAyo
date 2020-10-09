package com.goayo.debtify.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Oscar Sanner and Olof Sjögren
 * @date 2020-10-07
 */
public class EventBus {

    private static EventBus instance;
    private Map<Class<? extends IModelEvent>, List<IEventHandler>> listenerMap;

    private EventBus() {
        listenerMap = new HashMap<>();
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void register(IEventHandler handler, Class<? extends IModelEvent> eventClass) {
        if (listenerMap.get(eventClass) == null) {
            listenerMap.put(eventClass, new ArrayList<>());
        }
        listenerMap.get(eventClass).add(handler);
    }

    public void unRegister(IEventHandler handler, Class<? extends IModelEvent> eventClass) {
        listenerMap.get(eventClass).remove(handler);
        //TODO: Handle null exception perhaps?
    }

    public <T extends IModelEvent> void publish(T event) {
        List<IEventHandler> eventHandlerList = listenerMap.get(event.getClass());
        if (eventHandlerList == null) {
            return;
            //Todo; Maybe something else?
        }
        for (IEventHandler handler : eventHandlerList) {
            handler.onModelEvent(event);
        }
    }
}
