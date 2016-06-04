package com.quew8.geng3d.rendering.modes;

import com.quew8.geng3d.geometry.Mesh3D;
import com.quew8.geng.geometry.Param;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreter;
import com.quew8.geng.rendering.modes.FixedSizeDataInterpreterImpl;
import com.quew8.geng.rendering.modes.GeometricObjectInterpreter;
import com.quew8.geng3d.geometry.Vertex3D;

/**
 *
 * @author Quew8
 */
public class Mesh3DDataInterpreter extends GeometricObjectInterpreter<Mesh3D, Vertex3D> {
    
    public Mesh3DDataInterpreter(Param... params) {
        super(params);
    }
    
    public static FixedSizeDataInterpreter<Mesh3D, Vertex3D> getFixedSize(int n, Param... params) {
        return new FixedSizeDataInterpreterImpl<Mesh3D, Vertex3D>(
                new Mesh3DDataInterpreter(params),
                Mesh3D.createPolygon(new Vertex3D[n])
        );
    }
}
