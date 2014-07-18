package com.quew8.geng.rendering;

import com.quew8.gmath.Vector;
import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.Colour;
import com.quew8.gutils.PlatformBackend;
import com.quew8.gutils.PlatformUtils;
import com.quew8.gutils.desktop.opengl.DesktopOpenGL;
import java.lang.reflect.Array;
import static com.quew8.gutils.opengl.OpenGL.*;
import java.nio.ByteBuffer;

/**
 *
 * @param <T> 
 * @param <S> 
 * @param <E> 
 * @author Quew8
 */
public abstract class ParticleEmitter<T, S, E extends Particle<T, S>> {
    public static final ByteBuffer vertexBuffer;
    private boolean active = false;
    private E[] particles;
    
    static {
        vertexBuffer = BufferUtils.createByteBuffer(28);
        if(PlatformUtils.getPlatformConstant() == PlatformBackend.DESKTOP_CONSTANT) {
            DesktopOpenGL.glEnable(DesktopOpenGL.GL_VERTEX_PROGRAM_POINT_SIZE);
        }
    }
    
    public ParticleEmitter(E[] particles) {
        this.particles = particles;
    }

    @SuppressWarnings("unchecked")
    public ParticleEmitter(Class<E> clazz, int n) {
        this((E[])Array.newInstance(clazz, n));
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void toggleActive() {
        setActive(!active);
    }
    
    public void update() {
        if(active) {
            for(int i = 0; i < particles.length; i++) {
                if(particles[i] == null || particles[i].update()) {
                    particles[i] = getParticle(i);
                }
            }
        }
    }
    
    public void draw(int size) {
        if(active) {
            drawParticles(particles, size);
        }
    }
    
    protected abstract E getParticle(int n);
    
    public void drawParticles(Particle[] particles, int size) {
        for(int i = 0; i < particles.length; i++) {
            Colour c = particles[i].getColour();
            Vector v = particles[i].getPosition();
            vertexBuffer.putFloat(v.getX()).putFloat(v.getY()).putFloat(v.getZ());
            vertexBuffer.putFloat(c.getRed()).putFloat(c.getGreen()).putFloat(c.getBlue());
            vertexBuffer.putInt(size);
            vertexBuffer.flip();
            glDrawArrays(GL_POINTS, 0, 1);
        }
    }
}
