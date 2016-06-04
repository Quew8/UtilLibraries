package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix;
import com.quew8.gmath.Matrix3;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 * 
 * @author Quew8
 *
 */
public class Vertex2D implements IVertex<Vertex2D> {
    private final Vector2 position;
    private final Vector2 texCoords;
    private final Colour colour;

    private Vertex2D(Vector2 position, Vector2 texCoords, Colour colour) {
        if(position == null) {
            throw new IllegalArgumentException("Cannot have null position");
        }
        this.position = position;
        this.texCoords = texCoords;
        this.colour = colour;
    }

    public Vertex2D(Vector2 position, Vector2 texCoords) {
        this(position, texCoords, null);
    }

    public Vertex2D(Vector2 position, Colour colour) {
        this(position, null, colour);
    }

    public Vertex2D(Vector2 position) {
        this(position, null, null);
    }

    @Override
    public void addData(ByteBuffer to, Param param) {
        switch(param) {
            case POS_X: to.putFloat(position.getX()); return;
            case POS_Y: to.putFloat(position.getY()); return;
            case TEX_U: to.putFloat(getTexCoordsChecked().getX()); return;
            case TEX_V: to.putFloat(getTexCoordsChecked().getY()); return;
            case COLOUR_R: to.putFloat(getColourChecked().getRed()); return;
            case COLOUR_G: to.putFloat(getColourChecked().getGreen()); return;
            case COLOUR_B: to.putFloat(getColourChecked().getBlue()); return;
            case COLOUR_A: to.putFloat(getColourChecked().getAlpha()); return;
            default: throw new IllegalArgumentException("Vertex2D has no param: " + param);
        }
    }
    
    @Override
    public Vertex2D transform(Matrix transform, Matrix3 normalMatrix) {
        throw new UnsupportedOperationException("Need lower dimension vector times in Matrix");
        /*return new Vertex2D(
                Matrix.times(new Vector2(), transform, position),
                texCoords != null ? new Vector2(texCoords) : null,
                colour != null ? new Colour(colour) : null
        );*/
    }
    
    @Override
    public Vertex2D transformTextureCoords(Image img) {
        return new Vertex2D(
                new Vector2().setXY(position),
                img.transformCoords(new Vector2(), getTexCoordsChecked()),
                colour != null ? new Colour().setRGBA(colour) : null
        );
    }

    private Vector2 getTexCoordsChecked() {
        if(texCoords != null) {
            return texCoords;
        } else {
            throw new IllegalArgumentException("Vertex3D does not contain texture coordinates");
        }
    }

    private Colour getColourChecked() {
        if(colour != null) {
            return colour;
        } else {
            throw new IllegalArgumentException("Vertex3D does not contain colour");
        }
    }
}
