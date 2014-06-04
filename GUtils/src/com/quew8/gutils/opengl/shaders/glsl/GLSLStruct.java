package com.quew8.gutils.opengl.shaders.glsl;

/**
 *
 * @author Quew8
 */
public class GLSLStruct extends GLSLCodeElement<GLSLStruct> {
    private final String name;
    private final GLSLVariable[] members;
    
    public GLSLStruct(String name, GLSLVariable[] members) {
        this.name = name;
        this.members = members;
    }
    
    @Override
    String getCode() {
        String code = "struct " + name + " {\n";
        for(int i = 0; i < members.length; i++) {
            code += members[i].getCode() + ";\n";
        }
        code += "}";
        return code;
    }
    
}
