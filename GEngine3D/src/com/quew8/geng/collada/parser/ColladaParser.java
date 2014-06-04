package com.quew8.geng.collada.parser;

import com.quew8.geng.collada.DataFactory;
import com.quew8.geng.collada.Scene;
import com.quew8.geng.geometry.TextureArea;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class ColladaParser extends XMLParser {
    private static final String
            SCENE = "scene";
    
    private SceneParser scene;
    
    public ColladaParser() {
        super(false, false);
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(SCENE, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                scene = ColladaParser.this.parseWith(element, new SceneParser());
            }
            
        });
        return to;
    }
    
    public <T, S> Scene<T, S> getScene(DataFactory<?, ?, T, S> factory, TextureArea texture) {
        return scene.getScene(factory, texture);
    }
    
}
