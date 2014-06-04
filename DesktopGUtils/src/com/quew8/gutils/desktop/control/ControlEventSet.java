package com.quew8.gutils.desktop.control;

/**
 *
 * @author Quew8
 */
public class ControlEventSet {
    private final ControlEvent[] events;

    protected ControlEventSet(ControlEvent[] events) {
        this.events = events;
    }

    protected int getLength() {
        return events.length;
    }

    protected int getControl(int i) {
        return events[i].getControl();
    }

    protected boolean getState(int i) {
        return events[i].getState();
    }

    protected void consume(int i) {
        events[i].consume();
    }
}
