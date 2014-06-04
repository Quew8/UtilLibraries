package com.quew8.geng.collada.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class VerticesParser extends XMLParser implements DataSource {
    private static final String
            INPUT = "input";
    
    private UnsharedInputParser input;
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(INPUT, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                input = VerticesParser.this.parseWith(element, new UnsharedInputParser());
            }
            
        });
        return to;
    }

    @Override
    public void putData(Object[] in, int offset, int[] indices) {
        input.getSource().putData(in, offset, indices);
    }

    @Override
    public void putData(Object[] in, int offset, int n) {
        input.getSource().putData(in, offset, n);
    }

    @Override
    public int getNValuesPerVertex() {
        return input.getSource().getNValuesPerVertex();
    }
    
    @Override
    public int getCount() {
        return input.getSource().getCount();
    }
}
