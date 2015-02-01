package com.quew8.geng.rendering.modes;

import com.quew8.geng.rendering.Particle;
import com.quew8.geng.rendering.SpriteBatcher;

/**
 *
 * @author Quew8
 * @param <T>
 */
public interface ParticleGenerator<T extends Particle<? extends SpriteBatcher<?>>> {
    public T genParticle(int i, int n);
    public void updateParticle(T t, int i, int n);
    public T[] getArray(int n);
}
