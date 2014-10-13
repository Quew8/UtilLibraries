package com.quew8.geng3d.rendering.modes.interfaces;

import com.quew8.geng.rendering.modes.interfaces.DataFactory;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public interface DataFactory1D extends DataFactory {
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, float depth);
    public void addData(ByteBuffer to, float x, float y, float z, float width, float height, float depth);
}
