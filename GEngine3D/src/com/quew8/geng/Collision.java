package com.quew8.geng;

import com.quew8.gmath.Vector;

/**
 * 
 * @author Quew8
 */
public class Collision {
    private final Vector reaction;
    private final float frictionCoefficient;
    private final float elasticity;
    
    public Collision(Vector reaction, float frictionCoefficient, float elasticity) {
        this.reaction = reaction;
        this.frictionCoefficient = frictionCoefficient;
        this.elasticity = elasticity;
    }
    
    public Vector getReaction() {
        return reaction;
    }
    
    public float getFrictionCoefficient() {
        return frictionCoefficient;
    }
    
    public float getElasticity() {
        return elasticity;
    }
}