package com.quew8.geng3d.models.collada;

import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 */
public class ColourAccessor {
    private final Colour colour;
    private final ColladaTexture texture;

    private ColourAccessor(Colour colour, ColladaTexture texture) {
        this.colour = colour;
        this.texture = texture;
    }

    public ColourAccessor(Colour colour) {
        this(colour, null);
    }

    public ColourAccessor(ColladaTexture texture) {
        this(null, texture);
    }

    public Colour getColour() {
        return colour;
    }

    public ColladaTexture getTexture() {
        return texture;
    }
}
