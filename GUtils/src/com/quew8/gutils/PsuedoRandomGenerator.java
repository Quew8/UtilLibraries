package com.quew8.gutils;

import java.util.Random;

/**
 *
 * @author Quew8
 */
public class PsuedoRandomGenerator {
    private static final Random r = new Random();
    private static float[] array = new float[10];
    private final long[] seeds;
    
    /**
     * 
     * @param seeds 
     */
    public PsuedoRandomGenerator(long... seeds) {
        this.seeds = seeds;
    }
    
    /**
     * 
     * @param x
     * @param width
     * @return 
     */
    public float[] generate1DRange(int x, int width, float[] result, int resultOff) {
        putRange(seeds[0], x, 0, width, result, 0);
        return result;
    }
    
    /**
     * 
     * @param in
     * @return 
     */
    public float generate1D(int x) {
        putRange(seeds[0], x, 0, 1, array, 0);
        return array[0];
    }
    
    /**
     * 
     * @param x
     * @param width
     * @param y
     * @param height
     * @return 
     */
    public float[][] generate2DRange(int x, int width, int y, int height, float[][] result) {
        array = ArrayUtils.ensureArrayLength(array, width + height);
        putRange(seeds[0], x, y, width, array, 0);
        putRange(seeds[1], y, x, height, array, width);
        for(int i = 0; i < width; i++) {
            result[i][0] = array[i];
            result[i][1] = array[width + i];
            normalize2D(result[i], 0);
            System.out.println(i + " = " + result[i][0] + ", " + result[i][1]);
        }
        return result;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    public float[] generate2D(int x, int y, float[] result, int arrayOff) {
        putRange(seeds[0], x, y, 1, result, arrayOff);
        putRange(seeds[1], y, x, 1, result, arrayOff + 1);
        normalize2D(result, arrayOff);
        return result;
    }
    
    private static void normalize2D(float[] array, int offset) {
        float mag = (float) Math.sqrt(
                ( array[offset]*array[offset] ) + 
                ( array[offset+1]*array[offset+1] )
                );
        array[offset] /= mag;
        array[offset+1] /= mag;
    }
    
    public static PsuedoRandomGenerator genFunction(Random r, int n) {
        long[] seeds = new long[n];
        for(int i = 0; i < n; i++) {
            seeds[i] = r.nextLong();
        }
        return new PsuedoRandomGenerator(seeds);
    }
    
    public static PsuedoRandomGenerator genFunction(int n) {
        return genFunction(GeneralUtils.getRandom(), n);
    }
    
    private static void putRange(long seed, int x, int y, int n, float[] array, int arrayOff) {
        int xOff = 
                x > 0 ? 
                x % 10 : 
                10 + ( x % 10 );
        int yOff = 
                y > 0 ? 
                y % 10 : 
                10 + ( y % 10 );
        r.setSeed(seed + ( ( x - xOff ) * 57 ) + ( ( y - yOff ) * 97 ));
        for(int i = 0; i < xOff; i++) {
            r.nextFloat();
        }
        for(int i = 0; i < n; i++) {
            if(i >= 10) {
                putRange(seed, x + 10, y, n - i, array, arrayOff + i);
                break;
            } else {
                array[arrayOff + i] = ( r.nextFloat() * 2 ) - 1;
            }
        }
    }
}
