package com.quew8.geng.rendering;

import com.quew8.geng.geometry.Plane;
import com.quew8.gutils.Clock;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class Particle<T extends SpriteBatcher<?>> implements Sprite<T> {
    private int timeRemaining;
    private final int lifeSpan;
    
    public Particle(int lifeSpan) {
        this.timeRemaining = lifeSpan;
        this.lifeSpan = lifeSpan;
    }
    
    protected boolean update() {
        timeRemaining -= Clock.getDelta();
        return timeRemaining <= 0;
    }
    
    public int getTimeRemaining() {
        return timeRemaining;
    }
    
    public int getLifeSpan() {
        return lifeSpan;
    }
}
