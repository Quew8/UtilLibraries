package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.rendering.modes.ParticleGenerator;
import com.quew8.geng2d.rendering.Particle2D;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;
import java.util.function.Function;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class FunctionParticleGenerator<T> implements ParticleGenerator<Particle2D<T>> {
    private final Function<Float, Vector2> func;
    private final float size;
    private final int lifeSpan;
    private final float offset, paraScale;
    private final Colour initial, fin;

    public FunctionParticleGenerator(Function<Float, Vector2> func, float size, 
            int lifeSpan, float offset, float paraScale, Colour initial, 
            Colour fin) {
        
        this.func = func;
        this.size = size;
        this.lifeSpan = lifeSpan;
        this.offset = offset;
        this.paraScale = paraScale;
        this.initial = initial;
        this.fin = fin;
    }


    public FunctionParticleGenerator(final Function<Float, Float> xFunc, 
            final Function<Float, Float> yFunc, float size, int lifeSpan, 
            float offset, float paraScale, Colour initial, Colour fin) {
        
        this(
                new Function<Float, Vector2>() {

                    @Override
                    public Vector2 apply(Float t) {
                        return new Vector2(
                                xFunc.apply(t),
                                yFunc.apply(t)
                        );
                    }

                },
                size, lifeSpan, offset, paraScale, initial, fin
        );
    }
    
    public FunctionParticleGenerator(Function<Float, Float> xFunc, 
            Function<Float, Float> yFunc, float size, int lifeSpan, 
            Colour initial, Colour fin) {
        
        this(xFunc, yFunc, size, lifeSpan, 0, 1, initial, fin);
    }

    public FunctionParticleGenerator(Function<Float, Float> xFunc, 
            Function<Float, Float> yFunc, float size, int lifeSpan, 
            Colour colour) {
        
        this(xFunc, yFunc, size, lifeSpan, colour, colour);
    }
    
    @Override
    public Particle2D genParticle(int i, int n) {
        float f = (float) i / n;
        return new Particle2D(getPosition(f), getColour(f), size, lifeSpan);
    }

    @Override
    public void updateParticle(Particle2D p, int i, int n) {
        float f = ((float) i / n) + 1 - ((float) p.getTimeRemaining() / p.getLifeSpan());
        if(f > 1) {
            f -= 1;
        }
        p.setPosition(getPosition(f));
        p.setColour(getColour(f));
    }
    
    @Override
    public Particle2D[] getArray(int n) {
        return new Particle2D[n];
    }
    
    private Colour getColour(float f) {
        return Colour.blendRGB(new Colour(), initial, f, fin);
    }
    
    private Vector2 getPosition(float f) {
        float t = offset + ( f * paraScale );
        return func.apply(t);
    }
}
