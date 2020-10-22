package com.goayo.debtify.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventBusTest {

    @Test
    public void registerAndUnregister() {
        MockViewModel mock = new MockViewModel();
        mock.subscribe(EventBus.EVENT.SPECIFIC_GROUP_EVENT);
        mock.subscribe(EventBus.EVENT.CONTACT_EVENT);
        mock.subscribe(EventBus.EVENT.GROUPS_EVENT);

        EventBus.INSTANCE.unRegister(mock, EventBus.EVENT.CONTACT_EVENT);
        EventBus.INSTANCE.unRegister(mock, EventBus.EVENT.SPECIFIC_GROUP_EVENT);
        EventBus.INSTANCE.unRegister(mock, EventBus.EVENT.GROUPS_EVENT);
    }

    @Test
    public void publish() {
        MockViewModel mock = new MockViewModel();
        EventBus.INSTANCE.publish(EventBus.EVENT.CONTACT_EVENT);
        assertFalse(mock.published);

        mock.subscribe(EventBus.EVENT.CONTACT_EVENT);
        EventBus.INSTANCE.publish(EventBus.EVENT.CONTACT_EVENT);
        assertTrue(mock.published);
    }

    private static class MockViewModel implements IEventHandler{

        boolean published = false;
        void subscribe(EventBus.EVENT event){
            EventBus.INSTANCE.register(this, event);
        }

        @Override
        public void onModelEvent() {
            published = true;
        }
    }

}