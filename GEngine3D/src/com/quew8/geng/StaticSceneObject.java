package com.quew8.geng;

import com.quew8.geng.interfaces.Drawable;
import com.quew8.geng.rendering.StaticHandleInstance;
import com.quew8.gmath.Vector;

/**
 * 
 * @author Quew8
 *
 */
public class StaticSceneObject extends SceneObject<StaticHandleInstance> implements Drawable {
    private final Vector[] vertices;
    
    public StaticSceneObject(StaticHandleInstance drawable, Vector[] vertices) {
        super(drawable);
        this.vertices = vertices;
    }
    
    @Override
    public void draw() {
        getDrawable().draw();
    }
    
    @Override
    public Vector[] getVertices() {
        return vertices;
    }
    
}