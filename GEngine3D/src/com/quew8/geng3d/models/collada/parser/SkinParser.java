package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.collada.ColladaSkeleton;
import com.quew8.geng3d.models.DataFactory;
import com.quew8.geng3d.models.collada.Node;
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
public class SkinParser extends XMLParser {
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
    
    SkinParser() {
        
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(SOURCE, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            geometry = SkinParser.this.parseWith(attribute.getValue(), new GeometryParser());
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(BSM, (XMLElementParser) (Element element) -> {
            bsm = CollParseUtils.parseMatrix(element.getText());
        });
        to.put(JOINTS, (XMLElementParser) (Element element) -> {
            joints = SkinParser.this.parseWith(element, new JointsParser());
        });
        to.put(VERTEX_WEIGHTS, (XMLElementParser) (Element element) -> {
            vertexWeights = SkinParser.this.parseWith(element, new VertexWeightsParser());
        });
        return to;
    }
    
    public <T> T getSkin(Node[] nodes, DataFactory<?, ?, ?, T> factory, Image texture) {
        return factory.constructSkin(
                geometry.getVertexData(), 
                vertexWeights, 
                new ColladaSkeleton(bsm, joints.getJoints(nodes)), 
                texture
        );
    } 
}
