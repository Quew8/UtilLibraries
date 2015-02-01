package com.quew8.geng3d.geometry;

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
public class Vertex3D implements IVertex<Vertex3D> {
    private final Vector position, normal;
    private final Vector2 texCoords;
    private final Colour colour;

    private Vertex3D(Vector position, Vector normal, Vector2 texCoords, Colour colour) {
        if(position == null) {
            throw new IllegalArgumentException("Cannot have null position");
        }
        this.position = position;
        this.normal = normal;
        this.texCoords = texCoords;
        this.colour = colour;
    }

    public Vertex3D(Vector position, Vector normal, Vector2 texCoords) {
        this(position, normal, texCoords, null);
    }

    public Vertex3D(Vector position, Vector2 texCoords) {
        this(position, null, texCoords);
    }

    public Vertex3D(Vector position, Vector normal, Colour colour) {
        this(position, normal, null, colour);
    }

    public Vertex3D(Vector position, Colour colour) {
        this(position, null, colour);
    }

    public Vertex3D(Vector position) {
        this(position, null, null, null);
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getNormal() {
        return normal;
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
            case POS_Z: to.putFloat(position.getZ()); return;
            case NORMAL_X: to.putFloat(getNormalChecked().getX()); return;
            case NORMAL_Y: to.putFloat(getNormalChecked().getY()); return;
            case NORMAL_Z: to.putFloat(getNormalChecked().getZ()); return;
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
    public Vertex3D transform(Matrix transform, Matrix normalMatrix) {
        return new Vertex3D(
                Matrix.times(new Vector(), transform, position),
                normal != null ? Matrix.times(new Vector(), normalMatrix, position) : null,
                normal != null ? new Vector2(texCoords) : null,
                colour != null ? new Colour(colour) : null
        );
    }
    
    @Override
    public Vertex3D transformTextureCoords(Image img) {
        return new Vertex3D(
                new Vector(position),
                normal != null ? new Vector(normal) : null,
                img.transformCoords(new Vector2(), getTexCoordsChecked()),
                colour != null ? new Colour(colour) : null
        );
    }

    private Vector getNormalChecked() {
        if(normal != null) {
            return normal;
        } else {
            throw new IllegalArgumentException("Vertex3D does not contain normal");
        }
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.position);
        hash = 67 * hash + Objects.hashCode(this.normal);
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
        final Vertex3D other = (Vertex3D) obj;
        if(!Objects.equals(this.position, other.position)) {
            return false;
        }
        if(!Objects.equals(this.normal, other.normal)) {
            return false;
        }
        if(!Objects.equals(this.texCoords, other.texCoords)) {
            return false;
        }
        return Objects.equals(this.colour, other.colour);
    }

    @Override
    public String toString() {
        return "Vertex3D{" + position + ", " + normal + " " + texCoords + ", " + colour + "}";
    }
    
    public static Vector[] getPositions(Vertex3D[] vertices) {
        return Arrays.stream(vertices).map(new Function<Vertex3D, Vector>() {

            @Override
            public Vector apply(Vertex3D t) {
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
