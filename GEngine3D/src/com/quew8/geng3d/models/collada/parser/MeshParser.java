package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.DataInput;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.MeshDataFactory;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class MeshParser extends XMLParser{
    public static final String
            SOURCE = "source",
            VERTICES = "vertices",
            POLYLIST = "polylist";

    private PolylistParser polylistParser;
    
    MeshParser() {
        
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(SOURCE, (XMLElementParser) (Element element) -> {});
        to.put(VERTICES, (XMLElementParser) (Element element) -> {});
        to.put(POLYLIST, (XMLElementParser) (Element element) -> {
            polylistParser = MeshParser.this.parseWith(element, new PolylistParser());
        });
        return to;
    }
    
    protected DataInput getVertexData() {
        return polylistParser;
    }
    
    public <T> T getGeometry(MeshDataFactory<T, ?> factory, Image texture) {
        return polylistParser.getGeometry(factory, texture);
    }
}
