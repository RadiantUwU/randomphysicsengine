package com.radiant.randomphysicsengine.datatypes.events;

public class EventCaller implements Cloneable {
    public EventCaller() {}
    public EventCaller(Event e) {
        e.bindCaller(this);
    }
    EventCallerInterface interfaceObj;
    public void call(Object... args) {
        interfaceObj.call(args);
    }
    public EventCaller clone() {
        try {
            return (EventCaller) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
