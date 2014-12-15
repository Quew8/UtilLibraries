package com.quew8.gutils.desktop.control;

/**
 *
 * @author Quew8
 */
public class KeyDownControl {
    private final int key;
    
    public KeyDownControl(int key) {
        this.key = key;
    }

    protected void onIsPressed(WindowInputHandler handler) {}
    protected void onIsReleased(WindowInputHandler handler) {}
    
    protected int getKey() {
        return key;
    }
}
