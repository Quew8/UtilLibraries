package com.quew8.glslparser;

import com.quew8.geng.xmlparser.XMLParseException;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLExtraParser extends GLSLParser<GLSLExtraParser> {
    private String body;
    
    public DirectiveDesc getDirective() {
        return new GeneralDirectiveDesc(body);
    }
    
    @Override
    public void parse(Element element) {
        super.parse(element);
        body = element.getText().trim();
    }
    
    @Override
    public GLSLExtraParser getInstance() {
        return new GLSLExtraParser();
    }
    
    @Override
    public void setSource(GLSLExtraParser source) {
        this.body = source.body;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(body == null || body.isEmpty()) {
            return new XMLParseException("Body attribute in extra is empty");
        }
        return super.onParsingDone();
    }
    
    
}
