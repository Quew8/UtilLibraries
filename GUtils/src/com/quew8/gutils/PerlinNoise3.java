package com.quew8.gutils;

/**
 *
 * @author Quew8
 */
public class PerlinNoise3 {
    public static final int LINEAR_INTERPOLATION = 0, CUBIC_INTERPOLATION = 1;
    
    private final int interpolation;
    private final Octave[] octaves;
    private final PsuedoRandomGenerator noiseFunction;
    
    public PerlinNoise3(PsuedoRandomGenerator noiseFunction, int interpolation, float initAmp, int initlmd, float ampR, float lmdR, int nOctaves) {
        if(lmdR <= 0 || lmdR >= 1) {
            throw new IllegalArgumentException("Common Ratio of Lambda must lie in range 0 - 1 exclusive");
        }
        if(interpolation != LINEAR_INTERPOLATION && interpolation != CUBIC_INTERPOLATION) {
            throw new IllegalArgumentException("interpolation must be a constant defined in PerlinNoise");
        }
        this.interpolation = interpolation;
        this.noiseFunction = noiseFunction;
        this.octaves = new Octave[nOctaves];
        float amp = initAmp;
        int lmd = initlmd;
        for(int i = 0; i < nOctaves; i++, amp *= ampR, lmd *= lmdR) {
            this.octaves[i] = new Octave(amp, lmd);
        }
    }
    
    public float[] getNoiseRange(float start, float step, int n) {
        float[] values = new float[n];
        float f = start;
        for(int i = 0; i < n; i++, f += step) {
            values[i] = getNoise(f);
        }
        return values;
    }
    
    public float getNoise(float x) {
        float y = 0;
        for(int i = 0; i < octaves.length; i++) {
            int x1 = (int) (x - ( x % octaves[i].lambda ));
            int x2 = x1 + octaves[i].lambda;
            float t = ( x2 - x ) / ( octaves[i].lambda );
            float yi;
            switch(interpolation) {
                /*case LINEAR_INTERPOLATION: {
                    yi = GeneralUtils.linearInterpolate(
                            noiseFunction.generate(x1), 
                            noiseFunction.generate(x2), 
                            t);
                    break;
                }
                case CUBIC_INTERPOLATION: {
                    int x0 = x1 - octaves[i].lambda;
                    int x3 = x2 + octaves[i].lambda;
                    yi = GeneralUtils.cubicInterpolate(
                            noiseFunction.generate(x0), 
                            noiseFunction.generate(x1), 
                            noiseFunction.generate(x2), 
                            noiseFunction.generate(x3), 
                            t
                            );
                    break;
                }*/
                default: {
                    yi = Float.NaN;
                    break;
                }
            }
            y += yi * octaves[i].amplitude;
        }
        return y;
    }
    
    public float[][] getNoise2DRange(float startX, float stepX, int nx, float startY, float stepY, int ny) {
        float[][] values = new float[nx][ny];
        float f = startX;
        for(int i = 0; i < nx; i++, f += stepX) {
            float g = startY;
            for(int j = 0; j < ny; j++, g += stepY) {
                values[i][j] = getNoise2D(f, g);
            }
        }
        return values;
    }
    
    public float getNoise2D(float x, float y) {
        float z = 0;
        for(int i = 0; i < octaves.length; i++) {
            int x1 = (int) (x - ( x % octaves[i].lambda ));
            int x2 = x1 + octaves[i].lambda;
            float tx = ( x2 - x ) / ( octaves[i].lambda );
            int y1 = (int) (y - ( y % octaves[i].lambda ));
            int y2 = y1 + octaves[i].lambda;
            float ty = ( y2 - y ) / ( octaves[i].lambda );
            
            float zi;
            switch(interpolation) {
                /*case LINEAR_INTERPOLATION: {
                    zi = GeneralUtils.biLinearInterpolate(
                            noiseFunction.generate(x1, y1), noiseFunction.generate(x2, y1), 
                            noiseFunction.generate(x1, y2), noiseFunction.generate(x2, y2), 
                            tx, ty
                            );
                    break;
                }
                case CUBIC_INTERPOLATION: {
                    int x0 = x1 - octaves[i].lambda;
                    int x3 = x2 + octaves[i].lambda;
                    int y0 = y1 - octaves[i].lambda;
                    int y3 = y2 + octaves[i].lambda;
                    zi = GeneralUtils.biCubicInterpolate(
                            noiseFunction.generate(x0, y0), 
                            noiseFunction.generate(x1, y0), 
                            noiseFunction.generate(x2, y0), 
                            noiseFunction.generate(x3, y0), 
                            noiseFunction.generate(x0, y1), 
                            noiseFunction.generate(x1, y1), 
                            noiseFunction.generate(x2, y1), 
                            noiseFunction.generate(x3, y1), 
                            noiseFunction.generate(x0, y2), 
                            noiseFunction.generate(x1, y2), 
                            noiseFunction.generate(x2, y2), 
                            noiseFunction.generate(x3, y2), 
                            noiseFunction.generate(x0, y3), 
                            noiseFunction.generate(x1, y3), 
                            noiseFunction.generate(x2, y3), 
                            noiseFunction.generate(x3, y3), 
                            tx, ty);
                    break;
                }*/
                default: {
                    zi = Float.NaN;
                    break;
                }
            }
            z += zi * octaves[i].amplitude;
        }
        return z;
    }
    
    private static class Octave {
        private float amplitude;
        private int lambda;
        public Octave(float amplitude, int lambda) {
            this.amplitude = amplitude;
            this.lambda = lambda;
        }
    }
    
    public static PerlinNoise3 create(PsuedoRandomGenerator noiseFunction, int interpolation, float initAmp, int initLmd, float ampR, float lmdR, int nOctaves) {
        return new PerlinNoise3(noiseFunction, interpolation, initAmp, initLmd, ampR, lmdR, nOctaves);
    }
    
    public static PerlinNoise3 create(long seed, int interpolation, float initAmp, int initLmd, float ampR, float lmdR, int nOctaves) {
        return create(new PsuedoRandomGenerator(seed), interpolation, initAmp, initLmd, ampR, lmdR, nOctaves);
    }
    
    public static PerlinNoise3 create(PsuedoRandomGenerator noiseFunction, float initAmp, int initLmd, float ampR, float lmdR, int nOctaves) {
        return create(noiseFunction, CUBIC_INTERPOLATION, initAmp, initLmd, ampR, lmdR, nOctaves);
    }
    
    public static PerlinNoise3 create(long seed, float initAmp, int initLmd, float ampR, float lmdR, int nOctaves) {
        return create(new PsuedoRandomGenerator(seed), initAmp, initLmd, ampR, lmdR, nOctaves);
    }
}