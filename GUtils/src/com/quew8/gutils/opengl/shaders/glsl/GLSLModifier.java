package com.quew8.gutils.opengl.shaders.glsl;

/**
 *
 * @author Quew8
 */
public enum GLSLModifier {
    NONE(""), CONST("const"), UNIFORM("uniform"), ATTRIBUTE("attribute"), VARYING("varying");
    
    private final String code;
    
    private GLSLModifier(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
    
    public static GLSLModifier getModifier(String code) {
        switch(code) {
        case "": return NONE;
        case "const": return CONST;
        case "uniform": return UNIFORM;
        case "attribute": return ATTRIBUTE;
        case "varying": return VARYING;
        default: throw new GLSLIllegalOperationException("No Such GLSL Modifier: " + code);
        }
    }
}
