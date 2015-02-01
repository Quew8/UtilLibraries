package com.quew8.geng3d.collada.parser;

import com.quew8.geng3d.collada.ColladaSkeleton;
import com.quew8.geng3d.collada.DataFactory;
import com.quew8.geng3d.collada.Node;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gmath.Matrix;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class SkinParser extends XMLParser {
    private static final String
            SOURCE = "source";
    private static final String
            BSM = "bind_shape_matrix",
            JOINTS = "joints",
            VERTEX_WEIGHTS = "vertex_weights";
    
    private GeometryParser geometry;
    private Matrix bsm;
    private JointsParser joints;
    private VertexWeightsParser vertexWeights;
    
    public SkinParser() {
        super(false, true);
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(SOURCE, new XMLAttributeParser() {

            @Override
            public void parse(Attribute attribute, Element parent) {
                geometry = SkinParser.this.parseWith(attribute.getValue(), new GeometryParser());
            }
            
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(BSM, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                bsm = CollParseUtils.parseMatrix(element.getText());
            }
            
        });
        to.put(JOINTS, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                joints = SkinParser.this.parseWith(element, new JointsParser());
            }
            
        });
        to.put(VERTEX_WEIGHTS, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                vertexWeights = SkinParser.this.parseWith(element, new VertexWeightsParser());
            }
            
        });
        return to;
    }
    
    public <T> T getSkin(Node<Void, Void>[] nodes, DataFactory<?, ?, ?, T> factory, Image texture) {
        return factory.constructSkin(
                geometry.getVertexData(), 
                vertexWeights, 
                new ColladaSkeleton(bsm, joints.getJoints(nodes)), 
                texture
        );
    } 
}
