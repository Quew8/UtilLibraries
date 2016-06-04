package com.quew8.glslparser;

import com.quew8.codegen.glsl.SrcFile;
import com.quew8.geng.xmlparser.XMLElementParser;
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
        to.put(SHADER, (XMLElementParser) (Element element) -> {
            GLSLShaderParser parser = GLSLProgramParser.this.parseWith(element, new GLSLShaderParser());
            shaders.put(parser.getShaderType(), parser);
        });
        return to;
    }
    
    public SrcFile getVertex(int minGLSL, int maxGLSL) {
        return shaders.get("vertex").getShader(minGLSL, maxGLSL);
    }
    
    public SrcFile getFragment(int minGLSL, int maxGLSL) {
        return shaders.get("fragment").getShader(minGLSL, maxGLSL);
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
