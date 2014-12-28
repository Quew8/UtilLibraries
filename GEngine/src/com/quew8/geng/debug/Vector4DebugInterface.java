package com.quew8.geng.debug;

import com.quew8.gmath.Vector4;
import com.quew8.gutils.debug.DebugInterface;
import java.util.List;

/**
 *
 * @author Quew8
 */
public class Vector4DebugInterface implements DebugInterface {
    private final Vector4 v;

    public Vector4DebugInterface(Vector4 v) {
        this.v = v;
    }

    @Override
    public String debugGetValue(String param) {
        switch(param) {
            case "x":
                return Float.toString(v.getX());
            case "y":
                return Float.toString(v.getY());
            case "z":
                return Float.toString(v.getZ());
            case "w":
                return Float.toString(v.getW());
            case "xyzw":
                return Float.toString(v.getX()) + ", " + Float.toString(v.getY()) + ", " + Float.toString(v.getZ()) + ", " + Float.toString(v.getW());
            case "mag":
                return Float.toString(v.magnitude());
            default:
                return null;
        }
    }

    @Override
    public DebugInterface debugGetObj(String param) {
        return null;
    }

    @Override
    public String debugSetValue(String param, String... value) {
        switch(param) {
            case "x": {
                if(value.length != 1) {
                    return "Requires one element";
                }
                v.setX(Float.parseFloat(value[0]));
                return null;
            }
            case "y": {
                if(value.length != 1) {
                    return "Requires one element";
                }
                v.setY(Float.parseFloat(value[0]));
                return null;
            }
            case "z": {
                if(value.length != 1) {
                    return "Requires one element";
                }
                v.setZ(Float.parseFloat(value[0]));
                return null;
            }
            case "w": {
                if(value.length != 1) {
                    return "Requires one element";
                }
                v.setW(Float.parseFloat(value[0]));
                return null;
            }
            case "xyzw": {
                if(value.length != 4) {
                    return "Requires three element";
                }
                v.setX(Float.parseFloat(value[0]));
                v.setY(Float.parseFloat(value[1]));
                v.setZ(Float.parseFloat(value[2]));
                v.setW(Float.parseFloat(value[3]));
                return null;
            }
            case "mag": return "mag cannot be set";
        }
        return "No Such Param";
    }
    
    @Override
    public void debugOnChangeObj(String in) {
        
    }

    @Override
    public void debugAddAllParams(List<String> objs, List<String> vals) {
        vals.add("x");
        vals.add("y");
        vals.add("z");
        vals.add("w");
        vals.add("xyzw");
        vals.add("mag");
    }
}
