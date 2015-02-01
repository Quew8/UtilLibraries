package com.quew8.geng.rendering.modes;

import com.quew8.geng.geometry.Mesh2D;
import com.quew8.geng.geometry.Param;
import com.quew8.geng.geometry.Vertex2D;

/**
 *
 * @author Quew8
 */
public class Mesh2DInterpreter extends GeometricObjectInterpreter<Mesh2D, Vertex2D> {
    public static Mesh2DInterpreter 
            NORMAL_TEXTURED_INSTANCE = new Mesh2DInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z, 
                    Param.NORMAL_X, Param.NORMAL_Y, Param.NORMAL_Z,
                    Param.TEX_U, Param.TEX_V
            ),
            NORMAL_COLOUR_INSTANCE = new Mesh2DInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z, 
                    Param.NORMAL_X, Param.NORMAL_Y, Param.NORMAL_Z,
                    Param.COLOUR_R, Param.COLOUR_G, Param.COLOUR_B, Param.COLOUR_A
            );
    
    public Mesh2DInterpreter(Param... params) {
        super(params);
    }
}
