package com.quew8.geng.rendering;

import com.quew8.gmath.Vector;
import com.quew8.gutils.Clock;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public abstract class Particle<T, S> {
    private int timeRemaining;
    private final int lifeSpan;
    private T t;
    private S s;
    
    public Particle(int lifeSpan, T t, S s) {
        this.timeRemaining = lifeSpan;
        this.lifeSpan = lifeSpan;
        this.t = t;
        this.s = s;
    }
    
    protected boolean update() {
        timeRemaining -= Clock.getDelta();
        t = incPosition(t);
        s = incColour(s);
        return timeRemaining <= 0;
    }
    
    public Vector getPosition() {
        return getPosition(t);
    }
    
    public Colour getColour() {
        return getColour(s);
    }
    
    public int getTimeRemaining() {
        return timeRemaining;
    }
    
    public int getLifeSpan() {
        return lifeSpan;
    }
    
    public abstract T incPosition(T t);
    public abstract S incColour(S s);
    public abstract Vector getPosition(T t);
    public abstract Colour getColour(S s);
}
