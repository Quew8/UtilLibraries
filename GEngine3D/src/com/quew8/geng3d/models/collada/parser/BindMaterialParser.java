package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class BindMaterialParser extends XMLParser {
    private static final String
            TECH_COMMON = "technique_common",
            INSTANCE_MATERIAL = "instance_material";
    
    private InstanceMaterialParser material;
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(TECH_COMMON, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                material = BindMaterialParser.this.parseWith(element.element(INSTANCE_MATERIAL), new InstanceMaterialParser());
            }
            
        });
        return to;
    }
}
