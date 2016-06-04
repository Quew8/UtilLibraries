package com.quew8.geng.noise;

import com.quew8.gutils.debug.DebugObject;
import com.quew8.gutils.debug.DebugParam;
import com.quew8.gutils.debug.DebugFloatSliderField;
import com.quew8.gutils.debug.DebugIntSliderField;
import com.quew8.gutils.debug.DebugFloatInterpreter;
import com.quew8.gutils.debug.DebugIntInterpreter;

/**
 *
 * @author Quew8
 */
@DebugObject(name = "FractalNoise")
public class FractalNoise implements NoiseFunction {
    private final NoiseFunction noiseFunc;
    @DebugFloatSliderField(min = 1, max = 20, step = 1)
    @DebugParam(interpreter = DebugFloatInterpreter.class)
    private float amplitude;
    @DebugFloatSliderField(min = 0.005f, max = 0.05f, step = 0.005f)
    @DebugParam(interpreter = DebugFloatInterpreter.class)
    private float frequency;
    @DebugFloatSliderField(min = 0.05f, max = 0.5f, step = 0.05f)
    @DebugParam(interpreter = DebugFloatInterpreter.class)
    private float persistence;
    @DebugFloatSliderField(min = 4f, max = 6f, step = 0.1f)
    @DebugParam(interpreter = DebugFloatInterpreter.class)
    private float lacunarity;
    @DebugIntSliderField(min = 1, max = 10)
    @DebugParam(name = "n_octaves", interpreter = DebugIntInterpreter.class)
    private int nOctaves;
    
    public FractalNoise(NoiseFunction noiseFunc, float amplitude, float frequency, float persistence, float lacunarity, int nOctaves) {
        this.noiseFunc = noiseFunc;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
        this.nOctaves = nOctaves;
    }
    
    @Override
    public float getNoise(float x, float y) {
        float lAmp = amplitude;
        float lFreq = frequency;
        float noise = lAmp * noiseFunc.getNoise(x * lFreq, y * lFreq);
        for(int i = 1; i < nOctaves; i++) {
            lAmp *= persistence;
            lFreq *= lacunarity;
            noise += lAmp * noiseFunc.getNoise(x * lFreq, y * lFreq);
        }
        return noise;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public float getPersistence() {
        return persistence;
    }

    public void setPersistence(float persistence) {
        this.persistence = persistence;
    }

    public float getLacunarity() {
        return lacunarity;
    }

    public void setLacunarity(float lacunarity) {
        this.lacunarity = lacunarity;
    }

    public int getNOctaves() {
        return nOctaves;
    }

    public void setNOctaves(int nOctaves) {
        this.nOctaves = nOctaves;
    }
}
