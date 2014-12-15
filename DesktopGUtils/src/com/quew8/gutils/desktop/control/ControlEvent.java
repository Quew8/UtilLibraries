package com.quew8.gutils.desktop.control;

/**
 *
 * @author Quew8
 */
public class ControlEvent {
    protected static final ControlEvent MOUSE_MOVEMENT_EVENT = new ControlEvent(1, -1, -1) {
        @Override
        protected boolean isMouseMovement() {
            return true;
        }
    };
    private int key;
    private final int state;
    private final int mods;
    
    protected ControlEvent(int key, int state, int mods) {
        this.key = key;
        this.state = state;
        this.mods = mods;
    }
    
    protected void consume() {
        this.key = -1;
    }
    
    protected int getControl() {
        return key;
    }
    
    protected int getState() {
        return state;
    }
    
    protected int getMods() {
        return mods;
    }
    
    protected boolean isMouseMovement() {
        return false;
    }
    
    @Override
    public String toString() {
        return "ControlEvent{" + "key=" + key + ", state=" + state + ", mods=" + mods + '}';
    }
}