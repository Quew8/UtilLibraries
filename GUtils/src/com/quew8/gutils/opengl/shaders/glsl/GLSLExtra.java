package com.quew8.gutils.opengl.shaders.glsl;

/**
 *
 * @author Quew8
 */
public class GLSLExtra extends GLSLCodeElement<GLSLExtra> {
    private final String code;
    
    public GLSLExtra(String code) {
        this.code = code;
    }
    
    @Override
    String getCode() {
        return code;
    }
    
    public static GLSLExtra getVersion(String version) {
        return new GLSLExtra("#version " + version);
    }
    
    public static GLSLExtra getExtension(String extension, boolean enable) {
        return new GLSLExtra("#extension " + extension + (enable ? " : enable" : " : disable"));
    }
    
}
