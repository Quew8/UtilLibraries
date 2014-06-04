package com.quew8.gutils;

import com.quew8.gutils.func.Function;
import java.util.Random;

/**
 *
 * @author Quew8
 */
public class PerlinNoise {
    private static int gradiantOffset = 0;
    private static float[] gradients = new float[10];
    
    private static int gradiant2DXOffset = 0;
    private static int gradiant2DYOffset = 0;
    private static float[][] gradiants2D = new float[10][10];
    
    private final PsuedoRandomGenerator randomGen;
    
    public PerlinNoise(PsuedoRandomGenerator randomGen) {
        this.randomGen = randomGen;
    }
    
    public PerlinNoise(Random r, int dims) {
        this(PsuedoRandomGenerator.genFunction(r, dims));
    }
    
    public PerlinNoise(long... seeds) {
        this(new PsuedoRandomGenerator(seeds));
    }
    
    public float[][] getNoiseRange2D(float startX, float stepX, int nx, float startY, float stepY, int ny) {
        createRange2D(startX - 1, startX + 1 + ( stepX * nx ), startY - 1, startY + 1 + ( stepY * ny ));
        float[][] noise = new float[nx][ny];
        
        for(int x = 0; x < nx; x++) {
            for(int y = 0; y < ny; y++) {
                noise[x][y] = getNoisePremade2D(startX + ( x * stepX ), startY + ( y * stepY ));
                //System.out.println(x + " " + y + " = " + noise[x][y]);
            }
        }
        
        return noise;
    }
    
    public float getNoise2D(float x, float y) {
        createRange2D(x - 1, x + 1, y - 1, y + 1);
        return getNoisePremade2D(x, y);
    }
    
    public void createRange2D(float startX, float endX, float startY, float endY) {
        int iStartX = (int) Math.floor(startX);
        int iEndX = (int) Math.ceil(endX);
        int iStartY = (int) Math.floor(startY);
        int iEndY = (int) Math.ceil(endY);
        gradiant2DXOffset = iStartX;
        gradiant2DYOffset = iStartY;
        int nx = iEndX - iStartX;
        int ny = iEndY - iStartY;
        gradiants2D = ArrayUtils.ensureArrayLength(gradiants2D, nx, ny);
        randomGen.generate2DRange(iStartX, nx, iStartY, ny, gradiants2D);
    }
    
    public float getNoisePremade2D(float x, float y) {
        //System.out.println("Getting " + x + ", " + y + " -------------");
        float xr = x - gradiant2DXOffset;
        float yr = y - gradiant2DYOffset;
        
        int x0 = (int) Math.floor(xr);
        int x1 = x0 + 1;
        int y0 = (int) Math.floor(yr);
        int y1 = y0 + 1;
        
        float u = xr - x0;
        float v = yr - y0;
        
        if(u == 0 && v == 0) {
            //return gradiants2D[x0][0] + gradiants2D[x0][1];
        }
        
        float f1 = gradiants2D[x0][0] * u;
        float f2 = gradiants2D[x1][0] * ( 1 - u );
        float f3 = gradiants2D[y0][1] * v;
        float f4 = gradiants2D[y1][1] * ( 1 - v );
        
        float n00 = ( f1 ) + ( f3 );
        float n10 = ( f2 ) + ( f3 );
        float n01 = ( f1 ) + ( f4 );
        float n11 = ( f2 ) + ( f4 );
        
        float fu = interpolate(u);
        float fv = interpolate(v);
        
        //System.out.println("n00 = " + n00 + " n10 = " + n10 + " fu = " + fu);
        //System.out.println("n01 = " + n01 + " n11 = " + n11 + " fv = " + fv);
        
        float nx0 = ( n00 * fu ) + ( n10 * ( 1 - fu ) );
        float nx1 = ( n01 * fu ) + ( n11 * ( 1 - fu ) );
        float nxy = ( nx0 * fv ) + ( nx1 * ( 1 - fv ) );
        //System.out.println("nx0 = " + nx0 + " nx1 = " + nx1 + " nxy = " + nxy);
        return nxy;
    }
    
    public float[] getNoiseRange(float start, float step, int n) {
        createRange(start - 1, start + 1 + ( step * n ) );
        float[] noise = new float[n];
        for(int i = 0; i < n; i++) {
            noise[i] = getNoisePremade(start + ( i * step ));
        }
        return noise;
    }
    
    public float getNoise(float f) {
        createRange(f - 1, f + 1);
        return getNoisePremade(f);
    }
    
    public void createRange(float start, float end) {
        int iStart = (int) Math.floor(start);
        int iEnd = (int) Math.ceil(end);
        gradiantOffset = iStart;
        int n = iEnd - iStart;
        System.out.println(gradients.length);
        gradients = ArrayUtils.ensureArrayLength(gradients, n);
        System.out.println(gradients.length);
        randomGen.generate1DRange(iStart, n, gradients, 0);
    }
    
    public float getNoisePremade(float f) {
        float f2 = f - gradiantOffset;
        
        int x0 = (int) Math.floor(f2);
        int x1 = x0 + 1;
        
        float u = f2 - x0;
        float v = f2 - x1;
        
        float y0 = u * gradients[x0];
        float y1 = v * gradients[x1];
        float s = interpolate(u);
        return ( y0 * s ) + ( y1 * ( 1 - s ) );
    }
    
    public static float interpolate(float t) {
        return (float) (( 6 * Math.pow(t, 5) ) - ( 15 * Math.pow(t, 4) ) + ( 10 * Math.pow(t, 3) )); 
    }
    
    public class NoiseFunction implements Function<Float, Float> {
        
        @Override
        public Float f(Float s) {
            return getNoise(s);
        }
        
    }
    
    public class RangeNoiseFuntion implements Function<Float, Integer> {
        private final float[] range;
        
        public RangeNoiseFuntion(float start, float step, int n) {
            this.range = getNoiseRange(start, step, n);
        }
        
        @Override
        public Float f(Integer s) {
            return range[s];
        }
    }
    public class Noise2DFunction implements Function<Float, Float[]> {
        
        @Override
        public Float f(Float[] s) {
            return getNoise2D(s[0], s[1]);
        }
        
    }
    
    public class RangeNoise2DFuntion implements Function<Float, Integer[]> {
        private final float[][] range;
        
        public RangeNoise2DFuntion(float startX, float stepX, int nx, float startY, float stepY, int ny) {
            this.range = getNoiseRange2D(startX, stepX, nx, startY, stepY, ny);
        }
        
        @Override
        public Float f(Integer[] s) {
            return range[s[0]][s[1]];
        }
    }
}
