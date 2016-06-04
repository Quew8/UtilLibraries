package com.quew8.geng.noise;

import com.quew8.gmath.GMath;
import com.quew8.gutils.Clock;
import com.quew8.gutils.GeneralUtils;
import java.util.Random;

/**
 *
 * @author Quew8
 */
public class SimplexNoise implements NoiseFunction {
    public static final int PERM_TABLE_LENGTH = 256;
    private static final float SKEW_FACTOR_2D = (GMath.sqrt(3) - 1) / 2;
    private static final float UNSKEW_FACTOR_2D = (3 - GMath.sqrt(3)) / 6;
    private static final int grad3[][] = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1}, {0, 0}, {0, 1},
        {1, -1}, {1, 0}, {1, 1}
    };
    private final int p[];

    public SimplexNoise(int[] permTable) {
        this.p = permTable;
    }
    
    public SimplexNoise(Random r, int length) {
        this(GeneralUtils.genPermTable(r, length));
    }
    
    public SimplexNoise(Random r) {
        this(r, PERM_TABLE_LENGTH);
    }
    
    public SimplexNoise(long seed, int length) {
        this(new Random(seed), PERM_TABLE_LENGTH);
    }
    
    public SimplexNoise(long seed) {
        this(seed, PERM_TABLE_LENGTH);
    }
    
    public SimplexNoise() {
        this(Clock.getTime());
    }

    public void calculateAvg() {
        float x = 0, y = 0;
        float max = Float.MIN_VALUE, min = Float.MAX_VALUE;
        while(x <= 255) {
            while(y <= 255) {
                float n = getNoise(x, y);
                max = Math.max(max, n);
                min = Math.min(min, n);
                y += 0.5f;
            }
            y = 0;
            x += 0.5f;
        }
        System.out.println("Max = " + max + ", min = " + min);
    }
    
    @Override
    public float getNoise(float x, float y) {
        float f = skewFactor(x, y);
        float xi = x + f;
        float yi = y + f;
        int xi0 = GMath.floor(xi);
        int yi0 = GMath.floor(yi);
        int xi1, yi1;
        if(xi - xi0 > yi - yi0) {
            xi1 = xi0 + 1;
            yi1 = yi0;
        } else {
            xi1 = xi0;
            yi1 = yi0 + 1;
        }
        int xi2 = xi0 + 1;
        int yi2 = yi0 + 1;
        f = unskewFactor(xi0, yi0);
        float x0 = xi0 - f;
        float y0 = yi0 - f;
        f = unskewFactor(xi1, yi1);
        float x1 = xi1 - f;
        float y1 = yi1 - f;
        f = unskewFactor(xi2, yi2);
        float x2 = xi2 - f;
        float y2 = yi2 - f;
        
        float noise = 0;
        noise = addNoiseContrib(noise, x, y, x0, y0, xi0, yi0);
        noise = addNoiseContrib(noise, x, y, x1, y1, xi1, yi1);
        noise = addNoiseContrib(noise, x, y, x2, y2, xi2, yi2);
        return 70 * noise;
    }
    
    private float addNoiseContrib(float noise, float x, float y, float x0, float y0, int xi0, int yi0) {
        float dx = x - x0;
        float dy = y - y0;
        float d0 = 0.5f - (dx * dx) - (dy * dy);
        if(d0 > 0) {
            int yii0 = yi0 & (p.length - 1);
            int[] grad0 = grad3[p[(xi0 + p[yii0]) & (p.length - 1)] % grad3.length];
            d0 *= d0;
            d0 *= d0;
            noise += d0 * dot(grad0, dx, dy);
        }
        return noise;
    }

    private static float skewFactor(float x, float y) {
        return (x + y) * SKEW_FACTOR_2D;
    }

    private static float unskewFactor(float x, float y) {
        return (x + y) * UNSKEW_FACTOR_2D;
    }
    
    private static float dot(int g[], float x, float y) {
        return g[0] * x + g[1] * y;
    }
}
