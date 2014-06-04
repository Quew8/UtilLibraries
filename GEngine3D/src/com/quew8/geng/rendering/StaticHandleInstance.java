package com.quew8.geng.rendering;

/**
 *
 * @author Quew8
 */
public class StaticHandleInstance implements IStaticHandle {
    private final int nHandles;
    private int shouldDraw = 0;
    
    protected StaticHandleInstance(int nHandles) {
        this.nHandles = nHandles;
    }
    
    @Override
    public void draw() {
        shouldDraw = nHandles;
    }
    
    @Override
    public void drawn() {
        shouldDraw--;
    }
    
    @Override
    public boolean shouldDraw() {
        return shouldDraw > 0;
    }
}
