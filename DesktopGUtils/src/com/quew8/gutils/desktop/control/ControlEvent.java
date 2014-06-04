package com.quew8.gutils.desktop.control;

/**
 *
 * @author Quew8
 */
public class ControlEvent {
    private int key;
    private final boolean state;
    
    public ControlEvent(int key, boolean state) {
        this.key = key;
        this.state = state;
    }
    
    public void consume() {
        this.key = -1;
    }
    
    public int getControl() {
        return key;
    }
    
    public boolean getState() {
        return state;
    }
}