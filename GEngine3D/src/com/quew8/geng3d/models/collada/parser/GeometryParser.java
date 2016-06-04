package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.DataInput;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.MeshDataFactory;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GeometryParser extends XMLParser {
    private final static String 
            NAME = "name";
    private final static String
            MESH = "mesh";
    
    private String name;
    private MeshParser mesh;
    
    public GeometryParser() {
        
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(MESH, (XMLElementParser) (Element element) -> {
            mesh = GeometryParser.this.parseWith(element, new MeshParser());
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(NAME, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            name = attribute.getValue();
        });
        return to;
    }
    
    public String getName() {
        return name;
    }
    
    protected DataInput getVertexData() {
        return mesh.getVertexData();
    }
    
    public <T> T getGeometry(MeshDataFactory<T, ?> factory, Image texture) {
        return mesh.getGeometry(factory, texture);
    }
}
