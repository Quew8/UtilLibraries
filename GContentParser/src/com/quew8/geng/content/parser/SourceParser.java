package com.quew8.geng.content.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gutils.content.Source;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class SourceParser extends XMLParser {
    private static final String
            INPUT = "input",
            PARAM = "param";
    
    private String source;
    private final HashMap<String, String> params = new HashMap<String, String>();
    
    public SourceParser(String dir) {
        this.source = dir;
    }
    
    public SourceParser() {
        this("");
    }

    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(INPUT, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                source += element.getText();
            }
            
        });
        to.put(PARAM, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                ParamParser parser = SourceParser.this.parseWith(element, new ParamParser());
                params.put(parser.getKey(), parser.getValue());
            }
            
        });
        return to;
    }
    
    public Source getSource() {
        return new Source(source, params);
    }
}
