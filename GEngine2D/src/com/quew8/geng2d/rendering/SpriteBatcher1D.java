package com.quew8.geng2d.rendering;

import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng2d.geometry.DataFactory1D;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class SpriteBatcher1D<T> extends SpriteBatcher<T> {
    private final DataFactory1D<T> dataFactory;

    public SpriteBatcher1D(StaticRenderMode renderMode, DataFactory1D<T> dataFactory,
            FixedSizeDataInterpreter<T, ?> dataInterpreter, int maxN) {
        
        super(null, renderMode, dataInterpreter, maxN);
        this.dataFactory = dataFactory;
    }
    
    public void draw(Colour colour, float x, float y, float width, float height) {
        batch(dataFactory.construct(colour, x, y, width, height));
    }
    
    public void draw(float x, float y, float width, float height) {
        batch(dataFactory.construct(x, y, width, height));
    }
    
}
