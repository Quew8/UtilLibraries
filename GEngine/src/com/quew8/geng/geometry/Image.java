package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix3;
import com.quew8.gmath.Vector2;

/**
 * 
 * @author Quew8
 */
public class Image {
    public static final Image WHOLE = new Image();
    
    public final Vector2 transformCoords(Vector2 result, Vector2 coords) {
        float[] transformedCoords = transformCoords(coords.getX(), coords.getY());
        result.setX(transformedCoords[0]);
        result.setY(transformedCoords[1]);
        return result;
    }
    
    public final float[] transformCoords(float x, float y) {
        float[] fa = new float[]{x, y};
        transformCoords(fa, 0);
        return fa;
    }

    public void transformCoords(float[] coords, int offset) {
        
    }
    
    protected Matrix3 getTransform() {
        return new Matrix3();
    }
    
    public static Image getTransform(Matrix3 transform) {
        return new TransformImage(transform);
    }
    
    public static Image getRegion(float minX, float minY, float maxX, float maxY) {
        return new RegionImage(minX, minY, maxX, maxY);
    }
    
    public static Image getRotation(float theta) {
        Matrix3 m = Matrix3.makeTranslation(new Vector2(-0.5f, -0.5f));
        Matrix3 temp = Matrix3.rotate(new Matrix3(), m, theta);
        m = Matrix3.translate(m, temp, new Vector2(0.5f, 0.5f));
        return new TransformImage(m);
    }
    
    public static Image subImage(Image img, Image subimg) {
        return new TransformImage(subimg.getTransform().times(img.getTransform()));
    }
    
    public static Image rotateImage(Image img, float theta) {
        return subImage(img, getRotation(theta));
    }
    
    private static class RegionImage extends Image {
        private final float minX, minY, sizeX, sizeY;

        private RegionImage(float minX, float minY, float maxX, float maxY) {
            this.minX = minX;
            this.minY = minY;
            this.sizeX = maxX - minX;
            this.sizeY = maxY - minY;
        }

        @Override
        public void transformCoords(float[] coords, int offset) {
            coords[offset] *= sizeX; coords[offset] += minX;
            coords[offset+1] *= sizeY; coords[offset+1] += minY;
        }
        
        @Override
        protected Matrix3 getTransform() {
            Matrix3 m = Matrix3.makeScaling(new Matrix3(), sizeX, sizeY);
            Matrix3 m2 = Matrix3.translate(new Matrix3(), m, new Vector2(minX, minY));
            return m2;
        }

        @Override
        public String toString() {
            return "RegionImage{" + "minX=" + minX + ", minY=" + minY + ", sizeX=" + sizeX + ", sizeY=" + sizeY + '}';
        }
    }
    
    private static class TransformImage extends Image {
        private final Matrix3 m;

        private TransformImage(Matrix3 m) {
            this.m = m;
        }

        @Override
        public void transformCoords(float[] coords, int offset) {
            Vector2 v = Matrix3.times(new Vector2(), m, new Vector2(coords[offset], coords[offset + 1]));
            coords[offset] = v.getX();
            coords[offset + 1] = v.getY();
        }
        
        @Override
        protected Matrix3 getTransform() {
            return m;
        }
        
    }
}
