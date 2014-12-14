package com.quew8.gutils.desktop.control;

import com.quew8.gutils.collections.Bag;

/**
 *
 * @author Quew8
 */
public class ControlEventSet {
    private final Bag<ControlEvent> events = new Bag<ControlEvent>(ControlEvent.class);

    protected ControlEventSet() {
        
    }

    protected int getLength() {
        return events.size();
    }

    protected int getControl(int i) {
        return events.get(i).getControl();
    }

    protected int getState(int i) {
        return events.get(i).getState();
    }

    protected int getMods(int i) {
        return events.get(i).getMods();
    }
    
    protected void consume(int i) {
        events.get(i).consume();
    }
    
    protected void clear() {
        events.clear();
    }
    
    protected void add(ControlEvent evt) {
        events.add(evt);
    }
}
