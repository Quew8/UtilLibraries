package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLIntAttributeParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gmath.Matrix;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class AccessorParser extends XMLParser {
    private static final String 
            COUNT = "count",
            PARAM = "param",
            SOURCE = "source",
            STRIDE = "stride";
    
    private int count;
    private int stride;
    private String type;
    private ArrayParser sourceArray;
    private final ArrayList<Param> params = new ArrayList<Param>();

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(SOURCE, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            Element e = AccessorParser.this.findTarget(attribute.getValue());
            if(e.getName().matches("Name_array")) {
                sourceArray = AccessorParser.this.parseWith(attribute.getValue(), new ArrayParser.NameArrayParser());
            } else if(e.getName().matches("float_array")) {
                sourceArray = AccessorParser.this.parseWith(attribute.getValue(), new ArrayParser.FloatArrayParser());
            }
        });
        to.put(COUNT, (XMLIntAttributeParser) (int value, Element parent) -> {
            count = value;
        });
        to.put(STRIDE, (XMLIntAttributeParser) (int value, Element parent) -> {
            stride = value;
        });
        return to;
    }

    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(PARAM, (XMLElementParser) (Element element) -> {
            params.add(new Param(element));
        });
        return to;
    }
    
    void put(Object[] in, int offset, int index) {
        int srcPos = index * stride;
        int destPos = offset;
        for (Param p : params) {
            if (p.isNamed()) {
                p.getType().putIn(in, destPos, sourceArray, srcPos);
                destPos += p.getType().getNValues();
            }
            srcPos += p.getType().stride;
        }
    }
    
    int getNValuesPerIndex() {
        int n = 0;
        for (Param p : params) {
            if (p.isNamed()) {
                n += p.getType().getNValues();
            }
        }
        return n;
    }
    
    public int getCount() {
        return count;
    }
    
    class Param {
        private static final String NAME = "name";
        private static final String TYPE = "type";
        private final String name;
        private final ParamType type;

        Param(String name, String type) {
            this.name = name;
            this.type = ParamType.getType(type);
        }

        Param(Element element) {
            this(element.attributeValue(NAME), element.attributeValue(TYPE));
        }
        
        public boolean isNamed() {
            return name != null;
        }
        
        public ParamType getType() {
            return type;
        }
    }
    
    private abstract static class ParamType {
        public static final ParamType 
                FLOAT = new ParamType(1, 1) {
                    @Override
                    public void putIn(Object[] dest, int destPos, ArrayParser src, int srcPos) {
                        dest[destPos] = src.get(srcPos);
                    }
                },
                NAME = new ParamType(1, 1) {
                    @Override
                    public void putIn(Object[] dest, int destPos, ArrayParser src, int srcPos) {
                        dest[destPos] = src.get(srcPos);
                    }
                },
                FLOAT4x4 = new ParamType(1, 16) {
                    @Override
                    public void putIn(Object[] dest, int destPos, ArrayParser src, int srcPos) {
                        float[] data = new float[16];
                        for(int i = 0; i < 16; i++) {
                            data[i] = ((Float)src.get(srcPos+i));
                        }
                        Matrix m = new Matrix();
                        m.setDataFromCM(data, 0);
                        dest[destPos] = m;
                    }
                };
        
        private final int nValues;
        private final int stride;
        
        private ParamType(int nValues, int stride) {
            this.nValues = nValues;
            this.stride = stride;
        }
        
        public abstract void putIn(Object[] dest, int destPos, ArrayParser src, int srcPos);
        
        public int getNValues() {
            return nValues;
        }
        
        public int getStride() {
            return stride;
        }
        
        public static ParamType getType(String type) {
            switch(type) {
            case "float": return FLOAT;
            case "name": return NAME;
            case "float4x4": return FLOAT4x4;
            default: throw new RuntimeException("Unrecognized Type: " + type);
            }
        }
    }
}
