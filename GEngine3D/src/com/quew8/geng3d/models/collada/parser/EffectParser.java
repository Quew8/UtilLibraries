package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.collada.Effect;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class EffectParser extends XMLParser {
    private static final String PROFILE_COMMON = "profile_COMMON";
    
    private ProfileCommonParser profileCommon;
    
    EffectParser() {
        super(false, false);
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(PROFILE_COMMON, (XMLElementParser) (Element element) -> {
            profileCommon = EffectParser.this.parseWith(element, new ProfileCommonParser());
        });
        return to;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(profileCommon == null) {
            return new XMLParseException("Effect without profile_COMMON");
        }
        return super.onParsingDone();
    }
    
    public Effect getEffect() {
        return new Effect(profileCommon.getProfileCommon());
    }
}
