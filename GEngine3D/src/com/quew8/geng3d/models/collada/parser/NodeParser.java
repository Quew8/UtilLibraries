package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.collada.InstanceController;
import com.quew8.geng3d.models.collada.InstanceGeometry;
import com.quew8.geng3d.models.collada.Node;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gmath.GMath;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class NodeParser extends XMLParser {
    private static final String 
            NAME = "name",
            SID = "sid",
            TYPE = "type";
    private static final String
            MATRIX = "matrix",
            TRANSLATE = "translate",
            ROTATE = "rotate",
            SCALE = "scale",
            NODE = "node",
            INSTANCE_GEOM = "instance_geometry",
            INSTANCE_CONTROLLER = "instance_controller";
    
    private NodeParser parent;
    private String name;
    private String sid;
    private String type;
    private Matrix transform = new Matrix();
    private final ArrayList<NodeParser> nodes = new ArrayList<NodeParser>();
    private final ArrayList<InstanceGeometryParser> geometry = new ArrayList<InstanceGeometryParser>();
    private final ArrayList<InstanceControllerParser> controllers = new ArrayList<InstanceControllerParser>();
    
    NodeParser(NodeParser parent) {
        this.parent = parent;
    }
    
    @Override
    public void parse(Element element) {
        super.parse(element);
        if(parent == null) {
            Element parentElement = element.getParent();
            if(parentElement.getName().matches("node")) {
                parent = parseWith(parentElement, new NodeParser(null));
            } else {
                parent = null;
            }
        }
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(NAME, (XMLAttributeParser) (Attribute attribute, Element parent1) -> {
            name = attribute.getValue();
        });
        to.put(SID, (XMLAttributeParser) (Attribute attribute, Element parent1) -> {
            sid = attribute.getValue();
        });
        to.put(TYPE, (XMLAttributeParser) (Attribute attribute, Element parent1) -> {
            type = attribute.getValue();
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(MATRIX, (XMLElementParser) (Element element) -> {
            transform = Matrix.times(new Matrix(), transform, CollParseUtils.parseMatrix(element.getText()));
        });
        to.put(TRANSLATE, (XMLElementParser) (Element element) -> {
            Vector t = CollParseUtils.parseVec3(element.getText());
            transform = Matrix.times(new Matrix(), transform, Matrix.makeTranslation(new Matrix(), t));
        });
        to.put(ROTATE, (XMLElementParser) (Element element) -> {
            float[] r = CollParseUtils.parseVec4(element.getText());
            Vector v = new Vector(r[0], r[1], r[2]);
            transform = Matrix.times(new Matrix(), transform, Matrix.makeAxisRotation(new Matrix(), v, GMath.toRadians(r[3])));
        });
        to.put(SCALE, (XMLElementParser) (Element element) -> {
            Vector s = CollParseUtils.parseVec3(element.getText());
            transform = Matrix.times(new Matrix(), transform, Matrix.makeScaling(new Matrix(), s));
        });
        to.put(NODE, (XMLElementParser) (Element element) -> {
            nodes.add(NodeParser.this.parseWith(element, new NodeParser(NodeParser.this)));
        });
        to.put(INSTANCE_GEOM, (XMLElementParser) (Element element) -> {
            geometry.add(NodeParser.this.parseWith(element, new InstanceGeometryParser()));
        });
        to.put(INSTANCE_CONTROLLER, (XMLElementParser) (Element element) -> {
            controllers.add(NodeParser.this.parseWith(element, new InstanceControllerParser()));
        });
        return to;
    }
    
    public Node getJoint() {
        if(!type.matches("JOINT")) {
            throw new RuntimeException("Node is not a joint");
        }
        Node[] children = new Node[nodes.size()];
        for(int i = 0; i < children.length; i++) {
            children[i] = nodes.get(i).getJoint();
        }
        return new Node(
                name,
                sid,
                Node.Type.JOINT, 
                transform,
                children
        );
    }
    
    public Node getNode() {
        Node[] children = new Node[nodes.size()];
        for(int i = 0; i < children.length; i++) {
            children[i] = nodes.get(i).getNode();
        }
        InstanceGeometry[] childrenGeometry = new InstanceGeometry[geometry.size()];
        for(int i = 0; i < childrenGeometry.length; i++) {
            childrenGeometry[i] = geometry.get(i).getGeometry();
        }
        InstanceController[] childrenControllers = new InstanceController[controllers.size()];
        for(int i = 0; i < childrenControllers.length; i++) {
            childrenControllers[i] = controllers.get(i).getInstanceController();
        }
        return new Node(
                name,
                sid,
                Node.Type.valueOf(type),
                transform,
                children,
                childrenGeometry,
                childrenControllers
        );
    }
}
