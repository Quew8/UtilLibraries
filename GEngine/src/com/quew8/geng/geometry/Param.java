package com.quew8.geng.geometry;

/**
 *
 * @author Quew8
 */
public enum Param {
    POS_X, POS_Y, POS_Z,
    NORMAL_X, NORMAL_Y, NORMAL_Z,
    TEX_U, TEX_V,
    COLOUR_R, COLOUR_G, COLOUR_B, COLOUR_A,
    WEIGHT_0, WEIGHT_1, WEIGHT_2, WEIGHT_3, WEIGHT_4, WEIGHT_5, WEIGHT_6;
    
    public static final Param[] 
            XYZ = new Param[]{POS_X, POS_Y, POS_Z},
            XYZUV = new Param[]{POS_X, POS_Y, POS_Z, TEX_U, TEX_V},
            XYZRGB = new Param[]{POS_X, POS_Y, POS_Z, COLOUR_R, COLOUR_G, COLOUR_B},
            XYZRGBA = new Param[]{POS_X, POS_Y, POS_Z, COLOUR_R, COLOUR_G, COLOUR_B, COLOUR_A},
            XYZNXNYNZ = new Param[]{POS_X, POS_Y, POS_Z, NORMAL_X, NORMAL_Y, NORMAL_Z},
            XYZNXNYNZUV = new Param[]{POS_X, POS_Y, POS_Z, NORMAL_X, NORMAL_Y, NORMAL_Z, TEX_U, TEX_V},
            XYZNXNYNZRGB = new Param[]{POS_X, POS_Y, POS_Z, NORMAL_X, NORMAL_Y, NORMAL_Z, COLOUR_R, COLOUR_G, COLOUR_B},
            XYZNXNYNZRGBA = new Param[]{POS_X, POS_Y, POS_Z, NORMAL_X, NORMAL_Y, NORMAL_Z, COLOUR_R, COLOUR_G, COLOUR_B, COLOUR_A},
            XY = new Param[]{POS_X, POS_Y},
            XYUV = new Param[]{POS_X, POS_Y, TEX_U, TEX_V},
            XYRGB = new Param[]{POS_X, POS_Y, COLOUR_R, COLOUR_G, COLOUR_B},
            XYRGBA = new Param[]{POS_X, POS_Y, COLOUR_R, COLOUR_G, COLOUR_B, COLOUR_A};
    
    public static boolean containsPosition(Param[] params) {
        for(Param p: params) {
            if(p == POS_X || p == POS_Y || p == POS_Z) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsNormal(Param[] params) {
        for(Param p: params) {
            if(p == NORMAL_X || p == NORMAL_Y || p == NORMAL_Z) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsTextureCoords(Param[] params) {
        for(Param p: params) {
            if(p == TEX_U || p == TEX_V) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsColour(Param[] params) {
        for(Param p: params) {
            if(p == COLOUR_R || p == COLOUR_G || p == COLOUR_B || p == COLOUR_A) {
                return true;
            }
        }
        return false;
    }
}
