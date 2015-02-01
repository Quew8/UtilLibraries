package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.rendering.modes.ParticleGenerator;
import com.quew8.geng3d.rendering.Particle3D;
import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;
import java.util.function.Function;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class FunctionParticleGenerator<T> implements ParticleGenerator<Particle3D<T>> {
    private final Function<Float, Vector> func;
    private final float size;
    private final int lifeSpan;
    private final float offset, paraScale;
    private final Colour initial, fin;

    public FunctionParticleGenerator(Function<Float, Vector> func, float size, 
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
            final Function<Float, Float> yFunc, final Function<Float, Float> zFunc, 
            float size, int lifeSpan, float offset, float paraScale, Colour initial, 
            Colour fin) {
        
        this(
                new Function<Float, Vector>() {

                    @Override
                    public Vector apply(Float t) {
                        return new Vector(
                                xFunc.apply(t),
                                yFunc.apply(t),
                                zFunc.apply(t)
                        );
                    }

                },
                size, lifeSpan, offset, paraScale, initial, fin
        );
    }
    
    public FunctionParticleGenerator(Function<Float, Float> xFunc, 
            Function<Float, Float> yFunc, Function<Float, Float> zFunc, 
            float size, int lifeSpan, Colour initial, Colour fin) {
        
        this(xFunc, yFunc, zFunc, size, lifeSpan, 0, 1, initial, fin);
    }

    public FunctionParticleGenerator(Function<Float, Float> xFunc, 
            Function<Float, Float> yFunc, Function<Float, Float> zFunc, 
            float size, int lifeSpan, Colour colour) {
        
        this(xFunc, yFunc, zFunc, size, lifeSpan, colour, colour);
    }
    
    @Override
    public Particle3D genParticle(int i, int n) {
        float f = (float) i / n;
        return new Particle3D(getPosition(f), getColour(f), size, lifeSpan);
    }

    @Override
    public void updateParticle(Particle3D p, int i, int n) {
        float f = ((float) i / n) + 1 - ((float) p.getTimeRemaining() / p.getLifeSpan());
        if(f > 1) {
            f -= 1;
        }
        p.setPosition(getPosition(f));
        p.setColour(getColour(f));
    }
    
    @Override
    public Particle3D[] getArray(int n) {
        return new Particle3D[n];
    }
    
    private Colour getColour(float f) {
        return Colour.blendRGB(new Colour(), initial, f, fin);
    }
    
    private Vector getPosition(float f) {
        float t = offset + ( f * paraScale );
        return func.apply(t);
    }
}
