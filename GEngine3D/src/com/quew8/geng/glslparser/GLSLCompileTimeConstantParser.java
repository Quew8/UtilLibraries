package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.gutils.opengl.shaders.glsl.GLSLCompileTimeConstant;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLCompileTimeConstantParser extends GLSLParser<GLSLCompileTimeConstantParser> {
    private String name;
    private String defaultValue = null;

    public GLSLCompileTimeConstantParser() {
        super(new String[]{}, new String[]{NAME});
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(NAME, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                name = attribute.getValue();
            }
        });
        to.put(DEFAULT_VALUE, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                defaultValue = attribute.getValue();
            }
        });
        return to;
    }
    
    public GLSLCompileTimeConstant getConstant() {
        finalized();
        return new GLSLCompileTimeConstant(name, defaultValue);
    }

    @Override
    public GLSLCompileTimeConstantParser getInstance() {
        return new GLSLCompileTimeConstantParser();
    }

    @Override
    public void setSource(GLSLCompileTimeConstantParser source) {
        this.name = source.name;
        this.defaultValue = source.defaultValue;
    }
    
}
