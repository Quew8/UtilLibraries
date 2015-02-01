package com.quew8.geng3d.collada.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLFloatAttributeParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng.xmlparser.XMLTextParser;
import com.quew8.geng3d.collada.Asset;
import com.quew8.geng3d.collada.Asset.Contributor;
import com.quew8.geng3d.collada.Asset.Unit;
import com.quew8.geng3d.collada.Asset.UpAxis;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class AssetParser extends XMLParser {
    private static final String
            CONTRIBUTOR = "contributor",
            CREATED = "created",
            MODIFIED = "modified",
            UNIT = "unit",
            UP_AXIS = "up_axis";
    private final ArrayList<ContributorParser> contributors = new ArrayList<ContributorParser>();
    private XMLTextParser created;
    private XMLTextParser modified;
    private UnitParser unit;
    private UpAxisParser upAxis;
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(CONTRIBUTOR, new XMLElementParser() {

            @Override
            public void parse(Element element) {
                contributors.add(AssetParser.this.parseWith(element, new ContributorParser()));
            }
            
        });
        to.put(CREATED, new XMLElementParser() {

            @Override
            public void parse(Element element) {
                if(created != null) {
                    throw new XMLParseException("Cannot have multiple created elements");
                }
                created = AssetParser.this.parseWith(element, new XMLTextParser());
            }
            
        });
        to.put(MODIFIED, new XMLElementParser() {

            @Override
            public void parse(Element element) {
                if(modified != null) {
                    throw new XMLParseException("Cannot have multiple modified elements");
                }
                modified = AssetParser.this.parseWith(element, new XMLTextParser());
            }
            
        });
        to.put(UNIT, new XMLElementParser() {

            @Override
            public void parse(Element element) {
                if(unit != null) {
                    throw new XMLParseException("Cannot have multiple unit elements");
                }
                unit = AssetParser.this.parseWith(element, new UnitParser());
            }
            
        });
        to.put(UP_AXIS, new XMLElementParser() {

            @Override
            public void parse(Element element) {
                if(upAxis != null) {
                    throw new XMLParseException("Cannot have multiple up_axis elements");
                }
                upAxis = AssetParser.this.parseWith(element, new UpAxisParser());
            }
            
        });
        return to;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(created == null) {
            return new XMLParseException("Asset must have created element");
        }
        if(modified == null) {
            return new XMLParseException("Asset must have modified element");
        }
        return super.onParsingDone();
    }
    
    public Asset getAsset() {
        Contributor[] contribs = new Contributor[contributors.size()];
        for(int i = 0; i < contributors.size(); i++) {
            contribs[i] = contributors.get(i).getContributor();
        }
        return new Asset(
                contribs,
                created.getText(),
                modified.getText(),
                unit != null ? unit.getUnit() : new Unit("meter", 1),
                upAxis != null ? upAxis.getUpAxis() : UpAxis.Y_UP
        );
    }
    
    private static class ContributorParser extends XMLParser {
        private static final String 
                AUTHOR = "author", 
                AUTHORING_TOOL = "authoring_tool";
        
        private XMLTextParser author;
        private XMLTextParser authoringTool;

        @Override
        public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
            to = super.addElementParsers(to);
            to.put(AUTHOR, new XMLElementParser() {

                @Override
                public void parse(Element element) {
                    if(author != null) {
                        throw new XMLParseException("Cannot have more than one author element per contributor");
                    }
                    author = ContributorParser.this.parseWith(element, new XMLTextParser());
                }
                
            });
            to.put(AUTHORING_TOOL, new XMLElementParser() {

                @Override
                public void parse(Element element) {
                    if(authoringTool != null) {
                        throw new XMLParseException("Cannot have more than one authoring_tool element per contributor");
                    }
                    authoringTool = ContributorParser.this.parseWith(element, new XMLTextParser());
                }
                
            });
            return to;
        }
        
        public Contributor getContributor() {
            return new Contributor(author.getText(), authoringTool.getText());
        }
        
    }

    private static class UnitParser extends XMLParser {
        private static final 
                String NAME = "name", 
                METER = "meter";
        
        private String name;
        private float meter;

        @Override
        public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
            to = super.addAttributeParsers(to);
            to.put(NAME, new XMLAttributeParser() {

                @Override
                public void parse(Attribute attribute, Element parent) {
                    name = attribute.getValue();
                }
                
            });
            to.put(METER, new XMLFloatAttributeParser() {

                @Override
                public void parse(float value, Element parent) {
                    meter = value;
                }
                
            });
            return to;
        }
        
        public Unit getUnit() {
            return new Unit(name, meter);
        }
    }

    private static class UpAxisParser extends XMLTextParser {
        private UpAxis upAxis;

        @Override
        public XMLParseException onParsingDone() {
            try {
                upAxis = UpAxis.valueOf(getText());
            } catch(IllegalArgumentException ex) {
                return new XMLParseException("Value of up_axis: \"" + getText() + "\" is invalid");
            }
            return super.onParsingDone();
        }
        
        public UpAxis getUpAxis() {
            return upAxis;
        }
    }
    
}
