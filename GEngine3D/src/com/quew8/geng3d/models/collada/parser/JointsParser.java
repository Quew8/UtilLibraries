package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.collada.ColladaJoint;
import com.quew8.geng3d.models.collada.Node;
import com.quew8.geng3d.models.Semantic;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gmath.Matrix;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class JointsParser extends XMLParser {
    private static final String
            INPUT = "input";
    
    private UnsharedInputParser jointInput;
    private UnsharedInputParser matrixInput;
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(INPUT, (XMLElementParser) (Element element) -> {
            UnsharedInputParser input = JointsParser.this.parseWith(element, new UnsharedInputParser());
            if(input.getSemantic() == Semantic.JOINT) {
                jointInput = input;
            } else {
                matrixInput = input;
            }
        });
        return to;
    }
    
    public ColladaJoint[] getJoints(Node[] nodes) {
        String[] names = new String[jointInput.getCount()];
        jointInput.getSource().putData(names, 0, names.length);
        Matrix[] ibms = new Matrix[matrixInput.getCount()];
        matrixInput.getSource().putData(ibms, 0, ibms.length);
        HashMap<String, Matrix> sidToMatrix = new HashMap<String, Matrix>();
        for(int i = 0; i < names.length; i++) {
            sidToMatrix.put(names[i], ibms[i]);
        }
        return getJoints(nodes, sidToMatrix);
    }
    
    private ColladaJoint[] getJoints(Node[] nodes, HashMap<String, Matrix> sidToMatrix) {
        ColladaJoint[] joints = new ColladaJoint[nodes.length];
        for(int i = 0; i < nodes.length; i++) {
            joints[i] = new ColladaJoint(
                    nodes[i].getSID(),
                    nodes[i].getAbsoluteTransform(), 
                    sidToMatrix.get(nodes[i].getSID()),
                    getJoints(nodes[i].getAllChildNodes(), sidToMatrix)
            );
        }
        return joints;
    }
    
}
