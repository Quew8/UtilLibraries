package com.quew8.geng2d.rendering.modes.interfaces;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.rendering.modes.interfaces.DataFactory;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public interface DataFactory2D extends DataFactory {
    public void addData(ByteBuffer to, Image texture, float x, float y, float width, float height);
    public void addData(ByteBuffer to, Colour colour, float x, float y, float width, float height);
    public void addData(ByteBuffer to, float x, float y, float width, float height);
}
