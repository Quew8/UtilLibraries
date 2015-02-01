package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.collada.Node;
import com.quew8.geng.xmlparser.XMLParser;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class SkeletonParser extends XMLParser {
    private NodeParser node;
    
    @Override
    public void parse(Element element) {
        super.parse(element);
        node = SkeletonParser.this.parseWith(element.getText(), new NodeParser(null));
    }
    
    public Node getJoint() {
        return node.getJoint();
    }
}
