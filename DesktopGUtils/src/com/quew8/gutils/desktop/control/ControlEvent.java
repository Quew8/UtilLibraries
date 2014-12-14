package com.quew8.gutils.desktop.control;

/**
 *
 * @author Quew8
 */
public class ControlEvent {
    private int key;
    private final int state;
    private final int mods;
    
    public ControlEvent(int key, int state, int mods) {
        this.key = key;
        this.state = state;
        this.mods = mods;
    }
    
    public void consume() {
        this.key = -1;
    }
    
    public int getControl() {
        return key;
    }
    
    public int getState() {
        return state;
    }
    
    public int getMods() {
        return mods;
    }
}