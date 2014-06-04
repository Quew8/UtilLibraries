package com.quew8.gutils.opengl.shaders.glsl;

import com.quew8.gutils.formatting.LinePrefixFormatter;

/**
 *
 * @author Quew8
 */
public class GLSLMethod extends GLSLCodeElement<GLSLMethod> {
    private final String name;
    private final String code;
    private final GLSLType returnType;
    private final GLSLVariable[] params;
    private final GLSLVariable[] globalVariables;
    private final GLSLExtra[] extras;
    private final GLSLCompileTimeConstant[] constants;
    
    public GLSLMethod(String name, String code, GLSLType returnType, 
            GLSLVariable[] params, GLSLVariable[] globalVariables, 
            GLSLExtra[] extras, GLSLCompileTimeConstant[] constants) {
        
        this.name = name;
        this.code = code;
        this.returnType = returnType;
        this.params = params;
        this.globalVariables = globalVariables;
        this.extras = extras;
        this.constants = constants;
    }
    
    public GLSLCompileTimeConstant[] getConstants() {
        return constants;
    }
    
    public GLSLVariable[] getGlobalVariables() {
        return globalVariables;
    }

    public GLSLExtra[] getExtras() {
        return extras;
    }
    
    private String getDeclarationCode() {
        String dec = returnType.getCode() + " " + name + "(";
        if(params.length >= 1) {
            dec += params[0].getCode();
            for(int i = 1; i < params.length; i++) {
                dec += ", " + params[i].getCode();
            }
        }
        dec += ")";
        return dec;
    }
    
    @Override
    String getCode() {
        String result = getDeclaration().getCode() + " {\n";
        LinePrefixFormatter formatter = new LinePrefixFormatter("    ");
        formatter.readIn(code + "\n");
        result += formatter.getText();
        result += "\n}";
        return result;
    }
    
    public GLSLMethodDeclaration getDeclaration() {
        return new GLSLMethodDeclaration();
    }
    
    public class GLSLMethodDeclaration extends GLSLElement {
        
        @Override
        String getCode() {
            return getDeclarationCode();
        }
        
    }
    
    public static String formatMethod(String declaration, String code) {
        String result = declaration + " {\n";
        LinePrefixFormatter formatter = new LinePrefixFormatter("    ");
        formatter.readIn(code + "\n");
        result += formatter.getText();
        result += "\n}";
        return result;
    }
}
