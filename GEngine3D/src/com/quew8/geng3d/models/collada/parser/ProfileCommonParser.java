package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.collada.ProfileCommon;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class ProfileCommonParser extends XMLParser {
    private static final String TECHNIQUE = "technique";
    
    private TechniqueParser technique;
    
    ProfileCommonParser() {
        super(false, false);
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(TECHNIQUE, (XMLElementParser) (Element element) -> {
            technique = ProfileCommonParser.this.parseWith(element, new TechniqueParser());
        });
        return to;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(technique == null) {
            return new XMLParseException("profile_COMMON without technique");
        }
        return super.onParsingDone();
    }
    
    public ProfileCommon getProfileCommon() {
        return new ProfileCommon(technique.getTechnique());
    }
}
