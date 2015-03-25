package com.quew8.geng2d.rendering;

import com.quew8.geng.rendering.Particle;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class Particle2D<T> extends Particle<SpriteBatcher2D<T>> {
    private Vector2 position;
    private Colour colour;
    private float size;

    public Particle2D(Vector2 position, Colour colour, float size, int lifeSpan) {
        super(lifeSpan);
        this.position = position;
        this.colour = colour;
        this.size = size;
    }

    @Override
    public void draw(SpriteBatcher2D batcher) {
        batcher.draw(colour, position.getX(), position.getY(), size, size);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
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
