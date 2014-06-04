package com.quew8.gutils.opengl.shaders.glsl;

import java.util.ArrayList;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class GLSLCodeElement<T extends GLSLCodeElement<T>> extends GLSLElement {
    
    boolean isSame(T other) {
        return this.getCode().equals(other.getCode());
    }
    
    @SuppressWarnings("unchecked")
    static <T extends GLSLCodeElement<T>> T[] combineElements(T[] setA, T[] setB, T[] into) {
        ArrayList<T> newGlobals = new ArrayList<T>();
        for(int i = 0; i < setA.length; i++) {
            newGlobals.add(setA[i]);
        }
        for(int i = 0; i < setB.length; i++) {
            boolean shouldAdd = true;
            for(int k = 0; k < setA.length; k++) {
                if(setB[i].isSame(setA[k])) {
                    shouldAdd = false;
                    break;
                }
            }
            if(shouldAdd) {
                newGlobals.add(setB[i]);
            }
        }
        return newGlobals.toArray(into);
    }
}
