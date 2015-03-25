package com.quew8.geng.debug;

import com.quew8.gmath.Vector;
import com.quew8.gutils.debug.DebugException;
import com.quew8.gutils.debug.DebugInterface;
import static com.quew8.gutils.debug.DebugInterface.debugEnsureLength;
import static com.quew8.gutils.debug.DebugInterface.debugGetFloat;
import com.quew8.gutils.debug.DebugObjectNotFoundException;
import com.quew8.gutils.debug.DebugParamNotFoundException;
import com.quew8.gutils.debug.DebugSettingFinalParamException;

/**
 *
 * @author Quew8
 */
public class VectorDebugInterface implements DebugInterface {
    private final Vector v;

    public VectorDebugInterface(Vector v) {
        this.v = v;
    }

    @Override
    public String debugGetName() {
        return "vector";
    }

    @Override
    public String debugGetValue(String param) throws DebugParamNotFoundException {
        switch(param) {
            case "x":
                return Float.toString(v.getX());
            case "y":
                return Float.toString(v.getY());
            case "z":
                return Float.toString(v.getZ());
            case "xyz":
                return Float.toString(v.getX()) + ", " + Float.toString(v.getY()) + ", " + Float.toString(v.getZ());
            case "mag":
                return Float.toString(v.magnitude());
            default:
                throw new DebugParamNotFoundException(this, param);
        }
    }

    @Override
    public DebugInterface debugGetObj(String obj) throws DebugObjectNotFoundException {
        throw new DebugObjectNotFoundException(this, obj);
    }

    @Override
    public void debugSetValue(String param, String... values) throws DebugException {
        switch(param) {
            case "x": {
                debugEnsureLength(values, 1);
                v.setX(debugGetFloat(values[0]));
                break;
            }
            case "y": {
                debugEnsureLength(values, 1);
                v.setY(debugGetFloat(values[0]));
                break;
            }
            case "z": {
                debugEnsureLength(values, 1);
                v.setZ(debugGetFloat(values[0]));
                break;
            }
            case "xyz": {
                debugEnsureLength(values, 3);
                v.setX(debugGetFloat(values[0]));
                v.setY(debugGetFloat(values[1]));
                v.setZ(debugGetFloat(values[2]));
                break;
            }
            case "mag": throw new DebugSettingFinalParamException(param);
            default: throw new DebugParamNotFoundException(this, param);
        }
    }
    
    @Override
    public void debugOnChangeObj(String in) {
        
    }

    @Override
    public String[] debugGetParams() {
        return new String[]{
            "x", "y", "z", "xyz", "mag"
        };
    }

    @Override
    public String[] debugGetObjects() {
        return new String[]{};
    }
}
