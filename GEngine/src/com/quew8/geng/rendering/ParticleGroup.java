package com.quew8.geng.rendering;

import com.quew8.geng.interfaces.Updateable;
import com.quew8.geng.rendering.modes.ParticleGenerator;

/**
 *
 * @param <T>
 * @author Quew8
 * @param <S>
 */
public class ParticleGroup<T extends SpriteBatcher<?>, S extends Particle<T>> extends BatchingRenderObjGroup<T, S> implements Updateable {
    private final ParticleGenerator<S> generator;
    
    public ParticleGroup(T batcher, ParticleGenerator<S> generator, S[] particles) {
        super(batcher, fill(particles, generator));
        this.generator = generator;
    }

    public ParticleGroup(T batcher, ParticleGenerator<S> generator, int n) {
        this(batcher, generator, generator.getArray(n));
    }
    
    @Override
    public void update() {
        for(int i = 0; i < getNSprites(); i++) {
            if(getSprite(i) == null || getSprite(i).update()) {
                setSprite(i, generator.genParticle(i, getNSprites()));
            } else {
                generator.updateParticle(getSprite(i), i, getNSprites());
            }
        }
    }
    
    public static <S extends Particle<?>> S[] fill(S[] array, ParticleGenerator<S> generator) {
        for(int i = 0; i < array.length; i++) {
            array[i] = generator.genParticle(i, array.length);
        }
        return array;
    }
}
