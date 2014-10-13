package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.Plane;
import com.quew8.geng.rendering.Particle;
import com.quew8.gmath.Vector;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class Particle3D extends Particle<SpriteBatcher2D> {
    private Vector position;
    private Colour colour;
    private float size;

    public Particle3D(Vector position, Colour colour, float size, int lifeSpan) {
        super(lifeSpan);
        this.position = position;
        this.colour = colour;
        this.size = size;
    }

    @Override
    public void draw(SpriteBatcher2D batcher) {
        batcher.draw(colour, position.getX(), position.getY(), position.getZ(), size, size, Plane.BILLBOARD_PLANE);
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
