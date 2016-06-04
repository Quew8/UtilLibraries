package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.collada.ColourAccessor;
import com.quew8.geng3d.models.collada.Phong;
import com.quew8.gutils.Colour;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class PhongParser extends XMLParser {
    private static final String EMISSION = "emission",
            AMBIENT = "ambient",
            DIFFUSE = "diffuse",
            SPECULAR = "specular",
            SHININESS = "shininess",
            INDEX_OF_REFRACTION = "index_of_refraction";
    
    private ColourAccessor emission;
    private Colour ambient;
    private ColourAccessor diffuse;
    private ColourAccessor specular;
    private float shininess;
    private float indexOfRefraction;
    
    PhongParser() {
        super(false, false);
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(EMISSION, (XMLElementParser) (Element element) -> {
            emission = parseColourAccessor(element);
        });
        to.put(AMBIENT, (XMLElementParser) (Element element) -> {
            ambient = parseColourAccessor(element).getColour();
        });
        to.put(SPECULAR, (XMLElementParser) (Element element) -> {
            specular = parseColourAccessor(element);
        });
        to.put(DIFFUSE, (XMLElementParser) (Element element) -> {
            diffuse = parseColourAccessor(element);
        });
        to.put(SHININESS, (XMLElementParser) (Element element) -> {
            shininess = parseParameter(element);
        });
        to.put(INDEX_OF_REFRACTION, (XMLElementParser) (Element element) -> {
            indexOfRefraction = parseParameter(element);
        });
        return to;
    }
    
    private float parseParameter(Element element) {
        Element floatElem = element.element("float");
        if(floatElem != null) {
            Float[] val = new Float[1];
            CollParseUtils.parseFloatsInto(floatElem.getText(), val, 0, 1);
            return val[0];
        }
        throw new XMLParseException("Requires float element");
    }
    
    private ColourAccessor parseColourAccessor(Element element) {
        Element colour = element.element("color");
        if(colour != null) {
            Float[] rgba = new Float[4];
            CollParseUtils.parseFloatsInto(colour.getText(), rgba, 0, 4);
            return new ColourAccessor(new Colour(rgba[0], rgba[1], rgba[2], rgba[3]));
        }
        Element texture = element.element("texture");
        if(texture != null) {
            return new ColourAccessor(PhongParser.this.parseWith(texture, new TextureParser()).getTexture());
        }
        throw new XMLParseException("Requires color or texture element");
    }
    
    public Phong getPhong() {
        return new Phong(emission, ambient, diffuse, specular, shininess, indexOfRefraction);
    }
}
