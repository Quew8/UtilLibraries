package com.quew8.geng;

import com.quew8.geng.rendering.IHandle;
import com.quew8.geng.interfaces.Collidable;
import com.quew8.gmath.Vector;


/**
 * 
 * @author Quew8
 *
 * @param <T>
 */
public abstract class SceneObject<T extends IHandle> extends GameObject implements Collidable {
    private final T drawable;
    private float frictionCoefficient;
    private float elasticity;
    
    public SceneObject(T drawable) {
        this.drawable = drawable;
        this.frictionCoefficient = 1;
        this.elasticity = 1;
    }
    
    public float getFrictionCoefficient() {
        return frictionCoefficient;
    }
    
    public void setFrictionCoefficient(float frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }
    
    public float getElasticity() {
        return elasticity;
    }
    
    public void setElasticity(float elasticity) {
        this.elasticity = elasticity;
    }
    
    @Override
    public Collision onCollision(Vector momentum, Vector reaction) {
        return new Collision(reaction, getFrictionCoefficient(), getElasticity());
    }

    public T getDrawable() {
        return drawable;
    }
}
