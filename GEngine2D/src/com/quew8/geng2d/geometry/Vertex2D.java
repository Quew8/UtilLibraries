package com.quew8.geng2d.geometry;

import com.quew8.geng.geometry.IVertex;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Param;
import com.quew8.gmath.Matrix;

import com.quew8.gmath.Vector;
import com.quew8.gmath.Vector2;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * 
 * @author Quew8
 *
 */
public class Vertex2D implements IVertex<Vertex2D> {
    private final Vector2 position, texCoords;
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

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getTexCoords() {
        return texCoords;
    }

    public Colour getColour() {
        return colour;
    }

    @Override
    public void addData(ByteBuffer to, Param param) {
        switch(param) {
            case POS_X: to.putFloat(position.getX()); return;
            case POS_Y: to.putFloat(position.getY()); return;
            case POS_Z: throw new IllegalArgumentException("Vertex2D does not contain z coordinates");
            case NORMAL_X:
            case NORMAL_Y:
            case NORMAL_Z: throw new IllegalArgumentException("Vertex2D does not contain normal");
            case TEX_U: to.putFloat(getTexCoordsChecked().getX()); return;
            case TEX_V: to.putFloat(getTexCoordsChecked().getY()); return;
            case COLOUR_R: to.putFloat(getColourChecked().getRed()); return;
            case COLOUR_G: to.putFloat(getColourChecked().getGreen()); return;
            case COLOUR_B: to.putFloat(getColourChecked().getBlue()); return;
            case COLOUR_A: to.putFloat(getColourChecked().getAlpha()); return;
            default: throw new IllegalArgumentException("Unrecognized parameter: " + param);
        }
    }
    
    @Override
    public Vertex2D transform(Matrix transform, Matrix normalMatrix) {
        return new Vertex2D(
                Matrix.times(new Vector(), transform, new Vector(position)).getVector2(),
                texCoords != null ? new Vector2(texCoords) : null,
                colour != null ? new Colour(colour) : null
        );
    }
    
    @Override
    public Vertex2D transformTextureCoords(Image img) {
        return new Vertex2D(
                new Vector2(position),
                img.transformCoords(new Vector2(), getTexCoordsChecked()),
                colour != null ? new Colour(colour) : null
        );
    }

    private Vector2 getTexCoordsChecked() {
        if(texCoords != null) {
            return texCoords;
        } else {
            throw new IllegalArgumentException("Vertex2D does not contain texture coordinates");
        }
    }

    private Colour getColourChecked() {
        if(colour != null) {
            return colour;
        } else {
            throw new IllegalArgumentException("Vertex2D does not contain colour");
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.position);
        hash = 67 * hash + Objects.hashCode(this.texCoords);
        hash = 67 * hash + Objects.hashCode(this.colour);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final Vertex2D other = (Vertex2D) obj;
        if(!Objects.equals(this.position, other.position)) {
            return false;
        }
        if(!Objects.equals(this.texCoords, other.texCoords)) {
            return false;
        }
        return Objects.equals(this.colour, other.colour);
    }

    @Override
    public String toString() {
        return "Vertex2D{" + "position=" + position + ", texCoords=" + texCoords + ", colour=" + colour + '}';
    }
    
    public static Vector[] getPositions(Vertex2D[] vertices) {
        return Arrays.stream(vertices).map(new Function<Vertex2D, Vector2>() {

            @Override
            public Vector2 apply(Vertex2D t) {
                return t.getPosition();
            }
            
        }).toArray(new IntFunction<Vector[]>() {

            @Override
            public Vector[] apply(int value) {
                return new Vector[value];
            }
            
        });
    }
}
