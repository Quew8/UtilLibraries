package com.quew8.geng3d.collada.parser;

import com.quew8.geng3d.collada.DataFactory;
import com.quew8.geng3d.collada.Scene;
import com.quew8.geng.geometry.Image;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class ColladaParser extends XMLParser {
    private static final String
            ASSET = "asset",
            SCENE = "scene";
    
    private AssetParser asset;
    private SceneParser scene;
    
    public ColladaParser() {
        super(false, false);
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(ASSET, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                if(asset != null) {
                    throw new XMLParseException("COLLADA element cannot have more than one asset element");
                }
                asset = ColladaParser.this.parseWith(element, new AssetParser());
            }
            
        });
        to.put(SCENE, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                scene = ColladaParser.this.parseWith(element, new SceneParser());
            }
            
        });
        return to;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(asset == null) {
            return new XMLParseException("COLLADA element must have one asset element");
        }
        return super.onParsingDone();
    }
    
    public <T, S> Scene<T, S> getScene(DataFactory<?, ?, T, S> factory, Image texture) {
        return scene.getScene(asset.getAsset(), factory, texture);
    }
    
}
