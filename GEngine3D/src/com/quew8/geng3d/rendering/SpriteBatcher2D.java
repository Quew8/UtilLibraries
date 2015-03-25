package com.quew8.geng3d.rendering;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.geometry.Plane;
import com.quew8.geng.geometry.Texture;
import com.quew8.geng.rendering.SpriteBatcher;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng3d.geometry.DataFactory2D;
import com.quew8.gutils.Colour;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class SpriteBatcher2D<T> extends SpriteBatcher<T> {
    private final DataFactory2D<T> dataFactory;
    
    public SpriteBatcher2D(Texture tex, StaticRenderMode renderMode, DataFactory2D<T> dataFactory, 
            FixedSizeDataInterpreter<T, ?> dataInterpreter, int maxN) {
        
        super(tex, renderMode, dataInterpreter, maxN);
        this.dataFactory = dataFactory;
    }
    
    public SpriteBatcher2D(StaticRenderMode renderMode, DataFactory2D<T> dataFactory, 
            FixedSizeDataInterpreter<T, ?> dataInterpreter, int maxN) {
        
        this(null, renderMode, dataFactory, dataInterpreter, maxN);
    }
    
    public void draw(Image image, float x, float y, float z, float width, float height, Plane m) {
        batch(dataFactory.construct(image, x, y, z, width, height, m));
    }

    public void draw(Colour colour, float x, float y, float z, float width, float height, Plane m) {
        batch(dataFactory.construct(colour, x, y, z, width, height, m));
    }
    
    public void draw(float x, float y, float z, float width, float height, Plane m) {    
        batch(dataFactory.construct(x, y, z, width, height, m));
    }
}
