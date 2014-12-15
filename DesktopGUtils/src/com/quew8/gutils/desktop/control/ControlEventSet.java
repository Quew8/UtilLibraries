package com.quew8.gutils.desktop.control;

import com.quew8.gutils.collections.Bag;
import java.util.Arrays;

/**
 *
 * @author Quew8
 */
public class ControlEventSet {
    private final WindowInputHandler handler;
    private final Bag<ControlEvent> events = new Bag<ControlEvent>(ControlEvent.class);

    protected ControlEventSet(WindowInputHandler handler) {
        this.handler = handler;
    }

    protected WindowInputHandler getHandler() {
        return handler;
    }
    
    protected int getLength() {
        return events.size();
    }

    protected ControlEvent get(int i) {
        return events.get(i);
    }
    
    protected void clear() {
        events.clear();
    }
    
    protected void add(ControlEvent evt) {
        events.add(evt);
    }

    @Override
    public String toString() {
        return "ControlEventSet{" + Arrays.toString(events.getBackingArray()) + '}';
    }
}
