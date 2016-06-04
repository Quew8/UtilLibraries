package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.collada.InstanceGeometry;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class InstanceGeometryParser extends XMLParser {
    private static final String
            NAME = "name",
            URL = "url";
    private static final String
            BIND_MATERIAL = "bind_material";
    
    private String name;
    private GeometryParser geometry;
    private BindMaterialParser material;
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(NAME, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            name = attribute.getValue();
        });
        to.put(URL, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            geometry = InstanceGeometryParser.this.parseWith(attribute.getValue(), new GeometryParser());
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(BIND_MATERIAL, (XMLElementParser) (Element element) -> {
            material = InstanceGeometryParser.this.parseWith(element, new BindMaterialParser());
        });
        return to;
    }
    
    public InstanceGeometry getGeometry() {
        return new InstanceGeometry(name, geometry.getName(), geometry);
    }
}
