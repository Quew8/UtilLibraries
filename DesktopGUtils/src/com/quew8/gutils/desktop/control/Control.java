package com.quew8.gutils.desktop.control;

/**
 *
 * @author Quew8
 */
public class Control extends AbstractControl {
    public Control(int key) {
        super(key);
    }
    
    protected boolean isKey(int key) {
        return this.key == key;
    }
    
    protected void key(boolean down) {
        if(down) {
            onPressed();
        } else {
            onReleased();
        }
    }
    
    public void onPressed() {}
    
    public void onReleased() {}
}
