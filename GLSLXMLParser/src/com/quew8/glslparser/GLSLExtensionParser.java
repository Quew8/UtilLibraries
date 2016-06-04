package com.quew8.glslparser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLIntAttributeParser;
import com.quew8.geng.xmlparser.XMLParseException;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLExtensionParser extends GLSLParser<GLSLExtensionParser> {
    private String name;
    private boolean enable = true;
    private int belowVersion = ExtensionDesc.GLSL_MAX_VERSION;
    
    public DirectiveDesc getDirective() {
        return new ExtensionDesc(name, enable, belowVersion);
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(NAME, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            name = attribute.getValue();
        });
        to.put(ENABLE, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            enable = Boolean.parseBoolean(attribute.getValue());
        });
        to.put(BELOW, (XMLIntAttributeParser) (int value, Element parent) -> {
            if(value < ExtensionDesc.GLSL_MIN_VERSION || value > ExtensionDesc.GLSL_MAX_VERSION) {
                throw new XMLParseException("Below Version outside of glsl versions: " + value);
            }
            belowVersion = value;
        });
        return to;
    }

    @Override
    public GLSLExtensionParser getInstance() {
        return new GLSLExtensionParser();
    }

    @Override
    public void setSource(GLSLExtensionParser source) {
        this.name = source.name;
        this.enable = source.enable;
        this.belowVersion = source.belowVersion;
    }
    
    @Override
    public XMLParseException onParsingDone() {
        if(name == null || name.isEmpty()) {
            return new XMLParseException("Name attribute is empty in extension");
        }
        return super.onParsingDone();
    }
}
