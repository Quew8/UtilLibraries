package com.quew8.geng.noise;

import com.quew8.gmath.GMath;
import com.quew8.gutils.Clock;
import com.quew8.gutils.GeneralUtils;
import java.util.Random;

/**
 *
 * @author Quew8
 */
public class PerlinNoise implements NoiseFunction {
    public static final int PERM_TABLE_LENGTH = 256;
    private static final int grad3[][] = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1}, {0, 0}, {0, 1},
        {1, -1}, {1, 0}, {1, 1}
    };
    private final int p[];

    public PerlinNoise(int[] permTable) {
        this.p = permTable;
    }
    
    public PerlinNoise(Random r, int length) {
        this(GeneralUtils.genPermTable(r, length));
    }
    
    public PerlinNoise(Random r) {
        this(r, PERM_TABLE_LENGTH);
    }
    
    public PerlinNoise(long seed, int length) {
        this(new Random(seed), PERM_TABLE_LENGTH);
    }
    
    public PerlinNoise(long seed) {
        this(seed, PERM_TABLE_LENGTH);
    }
    
    public PerlinNoise() {
        this(Clock.getTime());
    }
    
    @Override
    public float getNoise(float x, float y) {
        int xi0 = GMath.floor(x);
        int yi0 = GMath.floor(y);
        float dx = x - xi0;
        float dy = y - yi0;
        int[] grad00 = grad3[(p[(xi0 + p[yi0 & (p.length - 1)]) & (p.length - 1)]) % grad3.length];
        int[] grad10 = grad3[(p[(xi0 + 1 + p[yi0 & (p.length - 1)]) & (p.length - 1)]) % grad3.length];
        int[] grad11 = grad3[(p[(xi0 + 1 + p[(yi0 + 1) & (p.length - 1)]) & (p.length - 1)]) % grad3.length];
        int[] grad01 = grad3[(p[(xi0 + p[(yi0 + 1) & (p.length - 1)]) & (p.length - 1)]) % grad3.length];
        float n00 = dot(grad00, dx, dy);
        float n10 = dot(grad10, dx - 1, dy);
        float n11 = dot(grad11, dx - 1, dy - 1);
        float n01 = dot(grad01, dx, dy - 1);
        float xAtten = fade(dx);
        float yAtten = fade(dy);
        float nx0 = mix(n00, n10, xAtten);
        float nx1 = mix(n01, n11, xAtten);
        float nxy = mix(nx0, nx1, yAtten);
        return nxy;
    }

    private static float dot(int g[], float x, float y) {
        return g[0] * x + g[1] * y;
    }

    private static float mix(float a, float b, float t) {
        return ((1 - t) * a) + (t * b);
    }

    private static float fade(float t) {
        return t * t * t * ((t * ((t * 6) - 15)) + 10);
    }
}
