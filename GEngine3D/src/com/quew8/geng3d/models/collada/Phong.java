package com.quew8.geng3d.models.collada;

import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class Phong {
    private final ColourAccessor emmision;
    private final Colour ambient;
    private final ColourAccessor diffuse;
    private final ColourAccessor specular;
    private final float shininess;
    private final float indexOfRefraction;

    public Phong(ColourAccessor emmision, Colour ambient, ColourAccessor diffuse, 
            ColourAccessor specular, float shininess, float indexOfRefraction) {
        
        this.emmision = emmision;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
        this.indexOfRefraction = indexOfRefraction;
    }

    public ColourAccessor getEmmision() {
        return emmision;
    }

    public Colour getAmbient() {
        return ambient;
    }

    public ColourAccessor getDiffuse() {
        return diffuse;
    }

    public ColourAccessor getSpecular() {
        return specular;
    }

    public float getShininess() {
        return shininess;
    }

    public float getIndexOfRefraction() {
        return indexOfRefraction;
    }
}
