package com.quew8.gutils.desktop.control;

import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Quew8
 */
public class Control {
    private static final IControl IMPL = (handler, mods) -> {};
    private final int key;
    private IControl pressedImpl;
    private IControl repeatImpl;
    private IControl releasedImpl;
    
    public Control(int key, IControl pressedImpl, IControl repeatImpl, IControl releasedImpl) {
        this.key = key;
        this.pressedImpl = pressedImpl;
        this.repeatImpl = repeatImpl;
        this.releasedImpl = releasedImpl;
    }
    
    public Control(int key) {
        this(key, IMPL, IMPL, IMPL);
    }
    
    protected void onAction(WindowInputHandler handler, int action, int mods) {
        switch(action) {
            case GLFW.GLFW_PRESS: onPressed(handler, mods); break;
            case GLFW.GLFW_REPEAT: onRepeat(handler, mods); break;
            case GLFW.GLFW_RELEASE: onReleased(handler, mods); break;
            default: throw new IllegalArgumentException();
        }
    }
    
    protected void onPressed(WindowInputHandler handler, int mods) {
        pressedImpl.onAction(handler, mods);
    }
    protected void onRepeat(WindowInputHandler handler, int mods) {
        repeatImpl.onAction(handler, mods);
    }
    protected void onReleased(WindowInputHandler handler, int mods) {
        releasedImpl.onAction(handler, mods);
    }

    protected int getKey() {
        return key;
    }

    public Control setPressedImpl(IControl pressedImpl) {
        this.pressedImpl = pressedImpl;
        return this;
    }

    public Control setRepeatImpl(IControl repeatImpl) {
        this.repeatImpl = repeatImpl;
        return this;
    }

    public Control setReleasedImpl(IControl releasedImpl) {
        this.releasedImpl = releasedImpl;
        return this;
    }
    
    public static interface IControl {
        public void onAction(WindowInputHandler handler, int mods);
    }
}
