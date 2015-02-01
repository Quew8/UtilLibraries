package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.geometry.Param;
import com.quew8.geng.rendering.modes.GeometricObjectInterpreter;
import com.quew8.geng3d.geometry.Skin;
import com.quew8.geng3d.geometry.WeightedVertex;

/**
 *
 * @author Quew8
 */
public class SkinInterpreter extends GeometricObjectInterpreter<Skin, WeightedVertex> {
    public static SkinInterpreter 
            NORMAL_TEXTURED_4W_INSTANCE = new SkinInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z, 
                    Param.NORMAL_X, Param.NORMAL_Y, Param.NORMAL_Z,
                    Param.TEX_U, Param.TEX_V,
                    Param.WEIGHT_0, Param.WEIGHT_1, Param.WEIGHT_2, Param.WEIGHT_3
            ),
            NORMAL_COLOUR_4W_INSTANCE = new SkinInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z, 
                    Param.NORMAL_X, Param.NORMAL_Y, Param.NORMAL_Z,
                    Param.COLOUR_R, Param.COLOUR_G, Param.COLOUR_B, Param.COLOUR_A,
                    Param.WEIGHT_0, Param.WEIGHT_1, Param.WEIGHT_2, Param.WEIGHT_3
            ),
            TEXTURED_4W_INSTANCE = new SkinInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z,
                    Param.TEX_U, Param.TEX_V,
                    Param.WEIGHT_0, Param.WEIGHT_1, Param.WEIGHT_2, Param.WEIGHT_3
            ),
            COLOUR_4W_INSTANCE = new SkinInterpreter(
                    Param.POS_X, Param.POS_Y, Param.POS_Z,
                    Param.COLOUR_R, Param.COLOUR_G, Param.COLOUR_B, Param.COLOUR_A,
                    Param.WEIGHT_0, Param.WEIGHT_1, Param.WEIGHT_2, Param.WEIGHT_3
            );
    
    public SkinInterpreter(Param... params) {
        super(params);
    }
    
}
