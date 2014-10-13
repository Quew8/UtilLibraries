package com.quew8.geng.content.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gutils.content.Source;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class SourceParser extends XMLParser {
    private static final String
            ID_STRING = "id",
            INPUT = "input",
            PARAM = "param",
            PARAM_LIST = "param_list";
    
    private String dir;
    private String idString = null;
    private final HashMap<Integer, String> indexedSources = new HashMap<Integer, String>();
    private final ArrayList<String> unindexedSources = new ArrayList<String>();
    private final HashMap<String, String> params = new HashMap<String, String>();
    private final HashMap<String, Entry<String, String>[]> paramLists = new HashMap<String, Entry<String, String>[]>();
    
    public SourceParser(String dir) {
        this.dir = dir;
    }
    
    public SourceParser() {
        this("");
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(ID_STRING, new XMLAttributeParser() {
            
            @Override
            public void parse(Attribute attribute, Element parent) {
                idString = attribute.getValue();
            }
            
        });
        to.put(INPUT, new XMLAttributeParser() {
            
            @Override
            public void parse(Attribute attribute, Element parent) {
                unindexedSources.add(attribute.getValue());
            }
            
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(INPUT, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                InputParser input = SourceParser.this.parseWith(element, new InputParser());
                if(input.getIndex() == -1) {
                    unindexedSources.add(input.getSource());
                } else {
                    indexedSources.put(input.getIndex(), input.getSource());
                }
            }
            
        });
        to.put(PARAM, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                ParamParser parser = SourceParser.this.parseWith(element, new ParamParser());
                params.put(parser.getKey(), parser.getValue());
            }
            
        });
        to.put(PARAM_LIST, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                ParamListParser parser = SourceParser.this.parseWith(element, new ParamListParser());
                paramLists.put(parser.getKey(), parser.getParams());
            }
            
        });
        return to;
    }
    
    public Source getSource() {
        String[] sources = new String[indexedSources.size() + unindexedSources.size()];
        int j = 0;
        for(int i = 0; i < sources.length; i++) {
            if(indexedSources.containsKey(i)) {
                sources[i] = dir + indexedSources.get(i);
            } else {
                if(j >= unindexedSources.size()) {
                    throw new XMLParseException("Not enough unindexed sources to fill gaps in indexed sources");
                } else {
                    sources[i] = dir + unindexedSources.get(j++);
                }
            }
        }
        String id = idString == null ? getIdString(sources) : idString;
        return new Source(id, Arrays.asList(sources), params, paramLists);
    }
    
    private static String getIdString(String[] sources) {
        String s = getIdString(sources[0]);
        for(int i = 1; i < sources.length; i++) {
            s += getIdString(sources[i]);
        }
        return s;
    }
    
    private static String getIdString(String source) {
        int i = source.lastIndexOf('/');
        if(i == -1) {
            i = source.lastIndexOf('\\');
        }
        String id = source;
        if(i != -1) {
            id = id.substring(i + 1, id.length());
        }
        return id.replace('.', '_');
    }
}
