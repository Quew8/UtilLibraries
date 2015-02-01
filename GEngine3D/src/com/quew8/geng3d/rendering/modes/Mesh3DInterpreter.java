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
public class Mesh3DInterpreter extends GeometricObjectInterpreter<Mesh3D, Vertex3D> {
    public static Mesh3DInterpreter 
            NORMAL_TEXTURED_INSTANCE = new Mesh3DInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z, 
                    Param.NORMAL_X, Param.NORMAL_Y, Param.NORMAL_Z,
                    Param.TEX_U, Param.TEX_V
            ),
            NORMAL_COLOUR_INSTANCE = new Mesh3DInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z, 
                    Param.NORMAL_X, Param.NORMAL_Y, Param.NORMAL_Z,
                    Param.COLOUR_R, Param.COLOUR_G, Param.COLOUR_B
            ),
            NORMAL_COLOUR_ALPHA_INSTANCE = new Mesh3DInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z, 
                    Param.NORMAL_X, Param.NORMAL_Y, Param.NORMAL_Z,
                    Param.COLOUR_R, Param.COLOUR_G, Param.COLOUR_B, Param.COLOUR_A
            ),
            TEXTURED_INSTANCE = new Mesh3DInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z,
                    Param.TEX_U, Param.TEX_V
            ),
            COLOUR_INSTANCE = new Mesh3DInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z,
                    Param.COLOUR_R, Param.COLOUR_G, Param.COLOUR_B
            ),
            COLOUR_ALPHA_INSTANCE = new Mesh3DInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z,
                    Param.COLOUR_R, Param.COLOUR_G, Param.COLOUR_B, Param.COLOUR_A
            );
    
    public Mesh3DInterpreter(Param... params) {
        super(params);
    }
    
    public static FixedSizeDataInterpreter<Mesh3D, Vertex3D> getFixedSize(int n, Param... params) {
        return new FixedSizeDataInterpreterImpl<Mesh3D, Vertex3D>(
                new Mesh3DInterpreter(params),
                Mesh3D.createPolygon(new Vertex3D[n])
        );
    }
}
