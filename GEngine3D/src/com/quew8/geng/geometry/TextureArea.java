package com.quew8.geng.geometry;

/**
 * 
 * @author Quew8
 */
public class TextureArea {
    private final float minX, minY, sizeX, sizeY;

    public TextureArea(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.sizeX = maxX - minX;
        this.sizeY = maxY - minY;
    }

    public TextureArea(float[] mins, float[] maxs) {
        this(mins[0], mins[1], maxs[0], maxs[1]);
    }

    public TextureArea(float minX, float minY, float maxX, float maxY, TextureArea texture) {
        this(texture.transformCoords(minX, minY), texture.transformCoords(maxX, maxY));
    }

    public float[] transformCoords(float x, float y) {
        return new float[]{
            ( x * sizeX ) + minX,
            ( y * sizeY ) + minY
        };
    }

    public void transformCoords(float[] coords, int offset) {
        coords[offset] *= sizeX; coords[offset] += minX;
        coords[offset+1] *= sizeY; coords[offset+1] += minY;
    }

    @Override
    public String toString() {
        return "TextureArea\nMin: " + minX + ", " + minY + "\nSize: " 
                + sizeX + ", " + sizeY;
    }
}
