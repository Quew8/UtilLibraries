package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.SemanticSet;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLIntAttributeParser;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class InputParser extends UnsharedInputParser implements DataSource {
    private static final String 
            OFFSET = "offset",
            SET = "set";
    
    private int offset = 0;
    private int set = 0;
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(OFFSET, new XMLIntAttributeParser() {

            @Override
            public void parse(int value, Element parent) {
                offset = value;
            }
            
        });
        to.put(SET, new XMLIntAttributeParser() {

            @Override
            public void parse(int value, Element parent) {
                set = value;
            }
            
        });
        return to;
    }
    
    @Override
    public int getNValuesPerVertex() {
        return getSource().getNValuesPerVertex();
    }
    
    public SemanticSet getSemanticSet() {
        return new SemanticSet(getSemantic(), set);
    }

    public int getOffset() {
        return offset;
    }
    
    @Override
    public void putData(Object[] in, int offset, int[] indices) {
        getSource().putData(in, offset, indices);
    }
    
    @Override
    public void putData(Object[] in, int offset, int n) {
        getSource().putData(in, offset, n);
    }
    
    @Override
    public int getCount() {
        return getSource().getCount();
    }
}
