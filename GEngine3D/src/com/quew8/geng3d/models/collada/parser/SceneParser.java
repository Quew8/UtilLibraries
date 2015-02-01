package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.DataFactory;
import com.quew8.geng3d.models.collada.Scene;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.collada.Asset;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class SceneParser extends XMLParser {
    private static final String
            INSTANCE_VIS_SCENE = "instance_visual_scene";
    
    private VisualSceneParser visualScene;
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(INSTANCE_VIS_SCENE, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                visualScene = SceneParser.this.parseWith(element.attributeValue("url"), new VisualSceneParser());
            }
            
        });
        return to;
    }
    
    public Scene getScene(Asset asset) {
        return new Scene(asset, visualScene.getVisualScene());
    }
}
