package com.quew8.geng.content.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gutils.ResourceLoader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.content.SourceSheet;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class SourceSheetParser extends XMLParser {
    private static final String 
            GROUP = "group";
    private static final String
            EXTERNAL = "external",
            ID_CLASS = "id_class",
            READER_CLASS = "reader_class";
    
    private boolean external = false;
    private final ArrayList<Source> sources = new ArrayList<Source>();
    private String idClassName;
    private String readerClassName;
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(GROUP, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                GroupParser groupParser = SourceSheetParser.this.parseWith(element, new GroupParser());
                sources.addAll(groupParser.getSources());
            }
            
        });
        return to;
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(EXTERNAL, new XMLAttributeParser() {

            @Override
            public void parse(Attribute attribute, Element parent) {
                external = Boolean.parseBoolean(attribute.getValue());
            }
            
        });
        to.put(ID_CLASS, new XMLAttributeParser() {

            @Override
            public void parse(Attribute attribute, Element parent) {
                idClassName = attribute.getValue();
            }
            
        });
        to.put(READER_CLASS, new XMLAttributeParser() {

            @Override
            public void parse(Attribute attribute, Element parent) {
                readerClassName = attribute.getValue();
            }
            
        });
        return to;
    }
    
    public SourceSheet getSourceSheet() throws ClassNotFoundException {
        return new SourceSheet(
                external ? 
                        ResourceLoader.EXTERNAL : 
                        ResourceLoader.INTERNAL, 
                getIdClass(), 
                getReaderClass(), 
                getSources()
        );
    }
    
    public com.quew8.gutils.content.Source[] getSources() {
        return sources.toArray(new com.quew8.gutils.content.Source[sources.size()]);
    }
    
    public com.quew8.gutils.content.Source getSource(int i) {
        return sources.get(i);
    }
    
    public int getNSources() {
        return sources.size();
    }
    
    public String getIdClassName() {
        return idClassName;
    }
    
    private Class<?> getIdClass() throws ClassNotFoundException {
        return getClass().getClassLoader().loadClass(idClassName);
    }
    
    public String getReaderClassName() {
        return readerClassName;
    }
    
    private Class<?> getReaderClass() throws ClassNotFoundException {
        return getClass().getClassLoader().loadClass(readerClassName);
    }
}
