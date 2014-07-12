package com.quew8.geng.geometry;

/**
 * 
 * @author Quew8
 */
public class Image {
    private final float minX, minY, sizeX, sizeY;

    public Image(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.sizeX = maxX - minX;
        this.sizeY = maxY - minY;
    }

    public Image(float[] mins, float[] maxs) {
        this(mins[0], mins[1], maxs[0], maxs[1]);
    }

    public Image(float minX, float minY, float maxX, float maxY, Image img) {
        this(img.transformCoords(minX, minY), img.transformCoords(maxX, maxY));
    }

    public Image() {
        this(0, 0, 1, 1);
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
}
