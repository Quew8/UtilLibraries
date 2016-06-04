package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class NewParamParser extends XMLParser {
    private static final String SAMPLER2D = "sampler2D",
            SURFACE = "surface";
    
    private Sampler2DParser sampler2D;
    private SurfaceParser surface;
    
    public NewParamParser() {
        super(false, false);
    }

    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to.put(SAMPLER2D, (XMLElementParser) (Element element) -> {
            if(surface != null) {
                throw new XMLParseException("newparam cannot initialize multiple params");
            }
            sampler2D = NewParamParser.this.parseWith(element, new Sampler2DParser());
        });
        to.put(SURFACE, (XMLElementParser) (Element element) -> {
            if(sampler2D != null) {
                throw new XMLParseException("newparam cannot initialize multiple params");
            }
            surface = NewParamParser.this.parseWith(element, new SurfaceParser());
        });
        return super.addElementParsers(to);
    }

    public Sampler2DParser getSampler2D() {
        return sampler2D;
    }

    public SurfaceParser getSurface() {
        return surface;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(sampler2D == null && surface == null) {
            return new XMLParseException("newparam not initializing a param");
        }
        return super.onParsingDone();
    }
}
