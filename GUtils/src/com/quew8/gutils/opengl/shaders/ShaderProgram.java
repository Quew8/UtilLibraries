package com.quew8.gutils.opengl.shaders;

import com.quew8.gutils.opengl.GObject;
import static com.quew8.gutils.opengl.OpenGL.*;

/**
 * 
 * @author Quew8
 */
public class ShaderProgram extends GObject {
    private final Shader vertexShader;
    private final Shader fragmentShader;
    
    public ShaderProgram(String vertexSource, String fragmentSource, String[] attribs, int[] attribIndices) {
        super(genProgramId());
        this.vertexShader = new Shader(vertexSource, GL_VERTEX_SHADER);
        this.fragmentShader = new Shader(fragmentSource, GL_FRAGMENT_SHADER);
        glAttachShader(getId(), this.vertexShader.getId());
        glAttachShader(getId(), this.fragmentShader.getId());
        for(int i = 0; i < attribs.length; i++) {
        	glBindAttribLocation(getId(), attribIndices[i], attribs[i]);
        }
        glLinkProgram(getId());
        glGetProgramiv(getId(), GL_LINK_STATUS, idBuff);
        if (idBuff.get(0) != GL_TRUE) {
            throw new ProgramLinkException(glGetShaderInfoLog(getId()));
        }
    }
    
    public ShaderProgram(String vertexSource, String fragmentSource, String... attribs) {
    	this(vertexSource, fragmentSource, attribs, getAttribIndices(attribs.length));
    }
    
    public void tempUse() {
        glUseProgram(getId());
    }
    
    public void use() {
        tempUse();
        //ShaderUtils.setInUseShader(this);
    }
    
    @Override
    public void delete() {
        vertexShader.delete();
        fragmentShader.delete();
        glDeleteProgram(getId());
    }
    
    private static int genProgramId() {
        return glCreateProgram();
    }
    
    private static int[] getAttribIndices(int i) {
    	int[] a = new int[i];
    	for(int k = 0; k < i; k++) {
    		a[k] = k;
    	}
    	return a;
    }
}
