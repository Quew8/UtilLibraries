package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.collada.ColladaTexture;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class Sampler2DParser extends XMLParser {
    private static final String SOURCE = "source";
    
    private SurfaceParser surface;
    
    Sampler2DParser() {
        super(false, false);
    }

    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to.put(SOURCE, (XMLElementParser) (Element element) -> {
            surface = Sampler2DParser.this.parseWith(
                    CollParseUtils.findTargetWithSID(element, element.getText()), 
                    new NewParamParser()
            ).getSurface();
        });
        return super.addElementParsers(to);
    }

    @Override
    public XMLParseException onParsingDone() {
        if(surface == null) {
            return new XMLParseException("sampler2D without surface");
        }
        return super.onParsingDone();
    }
    
    public ColladaTexture.Sampler2D getSampler2D() {
        return new ColladaTexture.Sampler2D(surface.getSurface());
    }
}
