package com.quew8.geng.glslparser;

import com.quew8.codegen.glsl.GLSLCodeGenUtils;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.gutils.opengl.shaders.ShaderProgram;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLProgramParser extends GLSLParser<GLSLProgramParser> {
    
    private final HashMap<String, GLSLShaderParser> shaders = new HashMap<String, GLSLShaderParser>();
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(SHADER, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLShaderParser parser = GLSLProgramParser.this.parseWith(element, new GLSLShaderParser());
                shaders.put(parser.getShaderType(), parser);
            }
        });
        return to;
    }
    
    public ShaderProgram getProgram(HashMap<String, String> constants, String[] attribs) {
        String vertex = GLSLCodeGenUtils.generateGLSL(shaders.get("vertex").getShader(), constants);
        String fragment = GLSLCodeGenUtils.generateGLSL(shaders.get("fragment").getShader(), constants);
        return new ShaderProgram(vertex, fragment, attribs);
    }
    
    @Override
    public GLSLProgramParser getInstance() {
        return new GLSLProgramParser();
    }
    
    @Override
    public void setSource(GLSLProgramParser source) {
        this.shaders.putAll(source.shaders);
    }
    
}
