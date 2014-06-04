package com.quew8.geng.content.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
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
            ID_CLASS = "id_class",
            READER_CLASS = "reader_class";
    
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
        return new SourceSheet(getIdClass(), getReaderClass(), getSources());
    }
    
    private Source[] getSources() {
        return sources.toArray(new Source[sources.size()]);
    }
    
    private Class<?> getIdClass() throws ClassNotFoundException {
        return getClass().getClassLoader().loadClass(idClassName);
    }
    
    private Class<?> getReaderClass() throws ClassNotFoundException {
        return getClass().getClassLoader().loadClass(readerClassName);
    }
}
