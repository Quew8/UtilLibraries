package com.quew8.geng3d.collada.parser;

import com.quew8.geng3d.collada.ColladaSkeleton;
import com.quew8.geng3d.collada.DataFactory;
import com.quew8.geng3d.collada.InstanceController;
import com.quew8.geng3d.collada.Node;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class InstanceControllerParser extends XMLParser {
    private static final String
            URL = "url";
    private static final String
            SKELETON = "skeleton",
            BIND_MATERIAL = "bind_material";
    
    private final ArrayList<SkeletonParser> skeletons = new ArrayList<SkeletonParser>();
    private ControllerParser controller;
    private BindMaterialParser bindMaterial;
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(URL, new XMLAttributeParser() {

            @Override
            public void parse(Attribute attribute, Element parent) {
                controller = InstanceControllerParser.this.parseWith(attribute.getValue(), new ControllerParser());
            }
            
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(SKELETON, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                skeletons.add(InstanceControllerParser.this.parseWith(element, new SkeletonParser()));
            }
            
        });
        to.put(BIND_MATERIAL, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                bindMaterial = InstanceControllerParser.this.parseWith(element, new BindMaterialParser());
            }
            
        });
        return to;
    }
    
    @SuppressWarnings("unchecked")
    public <T> InstanceController<T> getInstanceController(DataFactory<?, ?, ?, T> factory, Image texture) {
        Node<Void, Void>[] joints = getArray(skeletons.size());
        for(int i = 0; i < joints.length; i++) {
            joints[i] = skeletons.get(i).getJoint();
        }
        return new InstanceController<T>(
                controller.getName(),
                controller.getSkin(joints, factory, texture)
        );
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T[] getArray(int length, T... array) {
        return Arrays.copyOf(array, length);
    }
}
