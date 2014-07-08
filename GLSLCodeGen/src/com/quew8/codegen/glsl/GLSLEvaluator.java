package com.quew8.codegen.glsl;

import com.quew8.codegen.Evaluator;
import com.quew8.codegen.FetchMethod;

/**
 *
 * @author Quew8
 */
public class GLSLEvaluator implements Evaluator<GLSLElement, GLSLEvaluator> {
    public static final GLSLEvaluator INSTANCE = new GLSLEvaluator();
    @SuppressWarnings("unchecked")
    private static final FetchMethod<GLSLEvaluator>[] METHODS = GLSLEvaluator.<FetchMethod<GLSLEvaluator>>getArray(
            new FetchMethod<GLSLEvaluator>("FOR_LOOP", "int, int, String") {

                @Override
                public Object run(GLSLEvaluator eval, Object[] args) {
                    return ( (String) args[0] ) + " LOOP BETWEEN " + ( (Integer) args[0] ) + " AND " + ( (Integer) args[1] );
                }
                
            }
    );
    
    private GLSLEvaluator() {
        
    }
    
    @Override
    public void prepare(GLSLElement element) {
        
    }
    
    @Override
    public FetchMethod<GLSLEvaluator>[] getFetchMethods() {
        return METHODS;
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T[] getArray(T... array) {
        return array;
    }
    
}
