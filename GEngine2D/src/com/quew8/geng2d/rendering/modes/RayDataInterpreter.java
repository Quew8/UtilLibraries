package com.quew8.geng2d.rendering.modes;

import com.quew8.geng.geometry.Param;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng2d.geometry.Ray;
import com.quew8.geng2d.geometry.Vertex2D;
import static com.quew8.gutils.opengl.OpenGL.GL_LINES;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class RayDataInterpreter extends FixedSizeDataInterpreter<Ray, Vertex2D> {
    private final Param[] params;

    public RayDataInterpreter(Param... params) {
        this.params = params;
    }

    @Override
    public Vertex2D[] toPositions(Ray t) {
        return new Vertex2D[]{t.a, t.b};
    }

    @Override
    public void addVertexData(ByteBuffer to, Ray t, Vertex2D[] positions) {
        addVertexData(to, t);
    }

    @Override
    public void addVertexData(ByteBuffer to, Ray t) {
        for(int j = 0; j < params.length; j++) {
            t.a.addData(to, params[j]);
        }
        for(int j = 0; j < params.length; j++) {
            t.b.addData(to, params[j]);
        }
    }

    @Override
    public int getMode() {
        return GL_LINES;
    }

    @Override
    public int getNVertices() {
        return 2;
    }

    @Override
    public int getNBytes() {
        return params.length * 8;
    }

    @Override
    public int[] getIndices() {
        return new int[]{0, 1};
    }

    @Override
    public int getNIndices() {
        return 2;
    }
    
}
