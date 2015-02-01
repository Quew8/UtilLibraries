package com.quew8.geng3d.geometry;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.geometry.Plane;
import com.quew8.geng.rendering.modes.DataFactory;
import com.quew8.gutils.Colour;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public interface DataFactory3D extends DataFactory {
    public void addData(ByteBuffer to, Image texture, float x, float y, float z, float width, float height, float depth, Plane m);
    public void addData(ByteBuffer to, Colour colour, float x, float y, float z, float width, float height, float depth, Plane m);
    public void addData(ByteBuffer to, float x, float y, float z, float width, float height, float depth, Plane m);
}
