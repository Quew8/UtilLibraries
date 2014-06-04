package com.quew8.geng.collada.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class SourceParser extends XMLParser implements DataSource {
    private static final String 
            ACCESSOR = "accessor",
            TECHNIQUE_COMMON = "technique_common";
    
    private AccessorParser accessor;
    
    public SourceParser() {
        super(false, false);
    }

    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(TECHNIQUE_COMMON, new XMLElementParser() {

            @Override
            public void parse(Element element) {
                accessor = SourceParser.this.parseWith(element.element(ACCESSOR), new AccessorParser());
            }

        });
        return to;
    }

    @Override
    public void putData(Object[] in, int offset, int n) {
        int nParams = accessor.getNValuesPerIndex();
        for(int i = 0; i < n; i++) {
            accessor.put(in, offset, i);
            offset += nParams;
        }
    }

    @Override
    public void putData(Object[] in, int offset, int[] indices) {
        int nParams = accessor.getNValuesPerIndex();
        for(int i = 0; i < indices.length; i++) {
            accessor.put(in, offset, indices[i]);
            offset += nParams;
        }
    }

    @Override
    public int getNValuesPerVertex() {
        return accessor.getNValuesPerIndex();
    }
    
    @Override
    public int getCount() {
        return accessor.getCount();
    }
}
