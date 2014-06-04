package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.gutils.opengl.shaders.ShaderProgram;
import com.quew8.gutils.opengl.shaders.glsl.ShaderFactory;
import com.quew8.gutils.opengl.shaders.glsl.formatting.Expansion;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLProgramParser extends GLSLParser<GLSLProgramParser> {
    private final static String 
            V_SHADER = "vertex",
            F_SHADER = "fragment";
    
    private final HashMap<String, GLSLShaderParser> shaders = new HashMap<String, GLSLShaderParser>();

    public GLSLProgramParser() {
        super(new String[]{V_SHADER, F_SHADER}, new String[]{});
    }
    
    public ShaderFactory getShaderFactoryForType(String type) {
        finalized();
        ShaderFactory factory = new ShaderFactory();
        shaders.get(type).addPipelines(factory);
        return factory;
    }
    
    public ShaderFactory getVertexShaderFactory() {
        return getShaderFactoryForType("vertex");
    }
    
    public ShaderFactory getFragmentShaderFactory() {
        return getShaderFactoryForType("fragment");
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(SHADER, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLShaderParser parser = GLSLProgramParser.this.parseWith(element, new GLSLShaderParser());
                hasRequiredElement(parser.getShaderType());
                shaders.put(parser.getShaderType(), parser);
            }
        });
        return to;
    }
    
    public ShaderProgram createProgram(String[][] constantNameValuePairs, 
            Expansion[] expansions, String[] attribs) {
        
        return ShaderFactory.createProgram(
                getVertexShaderFactory(), 
                getFragmentShaderFactory(), 
                constantNameValuePairs, 
                expansions, 
                attribs);
    }
    
    public ShaderProgram createProgram(Expansion[] expansions, String[] attribs) {
        return ShaderFactory.createProgram(
                getVertexShaderFactory(), 
                getFragmentShaderFactory(),
                expansions, 
                attribs);
    }
    
    public ShaderProgram createProgram(String[] attribs) {
        return ShaderFactory.createProgram(
                getVertexShaderFactory(),
                getFragmentShaderFactory(),
                attribs);
    }
    
    public ShaderProgram createProgram(ShaderFactory vertex, ShaderFactory fragment) {
        return ShaderFactory.createProgram(
                getVertexShaderFactory(),
                getFragmentShaderFactory());
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
