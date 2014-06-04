package com.quew8.gutils.opengl.shaders.glsl;

import java.util.ArrayList;

/**
 *
 * @author Quew8
 */
public class GLSLCompileTimeConstant {
    private final String name;
    private String value;
    
    public GLSLCompileTimeConstant(String name, String defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }
    
    public GLSLCompileTimeConstant(String name) {
        this(name, null);
    }
    
    public String getName() {
        return name;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getValue() {
        if(value == null) {
            throw new GLSLIllegalOperationException("No Value Set For Compile Time Constant: " + name);
        }
        return value;
    }
    
    public static GLSLCompileTimeConstant[] combineConstants(GLSLCompileTimeConstant[] setA, GLSLCompileTimeConstant[] setB) {
        ArrayList<GLSLCompileTimeConstant> newGlobals = new ArrayList<GLSLCompileTimeConstant>();
        for(int i = 0; i < setA.length; i++) {
            newGlobals.add(setA[i]);
        }
        for(int i = 0; i < setB.length; i++) {
            boolean shouldAdd = true;
            for(int k = 0; k < setA.length; k++) {
                if(setB[i].getName().equals(setA[k].getName())) {
                    shouldAdd = false;
                    break;
                }
            }
            if(shouldAdd) {
                newGlobals.add(setB[i]);
            }
        }
        return newGlobals.toArray(new GLSLCompileTimeConstant[newGlobals.size()]);
    }
}
