package com.quew8.geng.glslparser.constructor;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLIntAttributeParser;
import com.quew8.geng.xmlparser.XMLParseException;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLVersionParser extends GLSLParser<GLSLVersionParser> {
    private int min = VersionDesc.GLSL_MIN_VERSION;
    private int max = VersionDesc.GLSL_MAX_VERSION;

    public DirectiveDesc getDirective() {
        return new VersionDesc(min, max);
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(MIN, new XMLIntAttributeParser() {

            @Override
            public void parse(int value, Element parent) {
                if(value < VersionDesc.GLSL_MIN_VERSION) {
                    throw new XMLParseException("Minimum version number lower than oldest version");
                }
                min = value;
            }
            
        });
        to.put(MAX, new XMLIntAttributeParser() {

            @Override
            public void parse(int value, Element parent) {
                if(value > VersionDesc.GLSL_MAX_VERSION) {
                    throw new XMLParseException("Maximum version number greater than newest version");
                }
                max = value;
            }
            
        });
        return to;
    }
    
    @Override
    public GLSLVersionParser getInstance() {
        return new GLSLVersionParser();
    }

    @Override
    public void setSource(GLSLVersionParser source) {
        this.min = source.min;
        this.max = source.max;
    }
    
}
