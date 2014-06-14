package com.quew8.geng3d.collada.parser;

import com.quew8.geng3d.collada.DataFactory;
import com.quew8.geng3d.collada.DataInput;
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
class GeometryParser extends XMLParser {
    private final static String 
            NAME = "name";
    private final static String
            MESH = "mesh";
    
    private String name;
    private MeshParser mesh;
    
    public GeometryParser() {
        super(false, false);
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(MESH, new XMLElementParser() {

            @Override
            public void parse(Element element) {
                mesh = GeometryParser.this.parseWith(element, new MeshParser());
            }
            
        });
        return to;
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
        return to;
    }
    
    public String getName() {
        return name;
    }
    
    protected DataInput getVertexData() {
        return mesh.getVertexData();
    }
    
    public <T> T getGeometry(DataFactory<?, ?, T, ?> factory, TextureArea texture) {
        return mesh.getGeometry(factory, texture);
    }
}
