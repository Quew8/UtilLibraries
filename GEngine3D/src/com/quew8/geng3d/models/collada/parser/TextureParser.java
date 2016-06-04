package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.collada.ColladaTexture;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class TextureParser extends XMLParser {
    private static final String TEXCOORD = "texcoord",
            TEXTURE = "texture";
    
    private Sampler2DParser texture;
    private String texcoord;
    
    public TextureParser() {
        super(false, false);
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to.put(TEXTURE, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            texture = TextureParser.this.parseWith(
                    CollParseUtils.findTargetWithSID(parent, attribute.getValue()), 
                    new NewParamParser()
            ).getSampler2D();
        });
        to.put(TEXCOORD, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            texcoord = attribute.getValue();
        });
        return super.addAttributeParsers(to);
    }

    @Override
    public XMLParseException onParsingDone() {
        if(texcoord == null) {
            return new XMLParseException("texture without texcoord");
        }
        if(texture == null) {
            return new XMLParseException("texture without texture");
        }
        return super.onParsingDone();
    }
    
    public ColladaTexture getTexture() {
        return new ColladaTexture(texture.getSampler2D(), texcoord);
    }
}
