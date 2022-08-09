package com.radiant.randomphysicsengine.datatypes.events;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Event {
    public Event() {}
    private EventCallerInterface inf = null;
    protected ArrayList<EventHandle> handles;
    private void call(Object... args) {
        Stream<EventHandle> stream = handles.stream();
        stream.forEach(handle -> handle.onEvent(args));
    }
    public void bindCaller(EventCaller c) throws SecurityException{
        if (inf == null) {
            inf = new EventCallerInterface();
            c.interfaceObj = inf;
        } else {
            throw new SecurityException("Event already bound to a caller");
        }
    }
}
