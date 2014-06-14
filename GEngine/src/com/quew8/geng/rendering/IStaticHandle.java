package com.quew8.geng.rendering;

/**
 *
 * @author Quew8
 */
public interface IStaticHandle extends IHandle {
    
    public void draw();

    public void drawn();

    public boolean shouldDraw();
}
