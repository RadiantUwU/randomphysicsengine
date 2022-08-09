package com.radiant.randomphysicsengine.datatypes.events;

public class EventHandle implements Runnable {
    public EventHandle() {}
    protected Event ev = null;
    protected boolean binded = false;
    protected Object[] args = null;
    protected synchronized void call(Object[] args) {
        this.args = args;
        new Thread(this).start();
    }
    public synchronized void run() {
        if (binded) {
            this.onEvent(args);
        }
    }
    public void disconnect() {
        if (binded) {
            ev.handles.remove(this);
            binded = false;
        }
    }
    public void bind(Event ev) {
        if (binded) {
            disconnect();
        }
        this.ev = ev;
        this.ev.handles.add(this);
        binded = true;
    }
    public void onEvent(Object[] args) {}
}
