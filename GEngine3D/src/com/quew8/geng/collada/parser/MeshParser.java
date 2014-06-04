package com.quew8.geng.collada.parser;

import com.quew8.geng.collada.DataFactory;
import com.quew8.geng.collada.DataInput;
import com.quew8.geng.geometry.TextureArea;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class MeshParser extends XMLParser{
    public static final String
            POLYLIST = "polylist";

    private PolylistParser polylistParser;
    
    public MeshParser() {
        super(false, false);
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(POLYLIST, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                polylistParser = MeshParser.this.parseWith(element, new PolylistParser());
            }
            
        });
        return to;
    }
    
    protected DataInput getVertexData() {
        return polylistParser;
    }
    
    public <T> T getGeometry(DataFactory<?, ?, T, ?> factory, TextureArea texture) {
        return polylistParser.getGeometry(factory, texture);
    }
}
