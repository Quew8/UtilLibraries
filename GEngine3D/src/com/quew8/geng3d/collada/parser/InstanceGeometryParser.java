package com.quew8.geng3d.collada.parser;

import com.quew8.geng3d.collada.DataFactory;
import com.quew8.geng3d.collada.InstanceGeometry;
import com.quew8.geng.geometry.TextureArea;
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
            URL = "url";
    private static final String
            BIND_MATERIAL = "bind_material";
    
    private GeometryParser geometry;
    private BindMaterialParser material;
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(URL, new XMLAttributeParser() {

            @Override
            public void parse(Attribute attribute, Element parent) {
                geometry = InstanceGeometryParser.this.parseWith(attribute.getValue(), new GeometryParser());
            }
            
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(BIND_MATERIAL, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                material = InstanceGeometryParser.this.parseWith(element, new BindMaterialParser());
            }
            
        });
        return to;
    }
    
    public <T> InstanceGeometry<T> getGeometry(DataFactory<?, ?, T, ?> factory, TextureArea texture) {
        return new InstanceGeometry<T>(geometry.getName(), geometry.getGeometry(factory, texture));
    }
}
