package com.quew8.geng.content.parser.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 *
 * @author Quew8
 */
class JsonGroupParser {
    private static final String 
            SOURCES = "sources";
    private static final String
            DIR = "dir";
    
    private String dir = "";
    private final ArrayList<com.quew8.gutils.content.Source> sources = new ArrayList<com.quew8.gutils.content.Source>();
    
    public JsonGroupParser parse(JsonElement element) {
        if(element.isJsonObject()) {
            JsonObject groupObj = element.getAsJsonObject();
            dir = JsonSourceSheetParser.getString(groupObj, DIR, false, dir);
            if(groupObj.has(SOURCES)) {
                if(groupObj.get(SOURCES).isJsonArray()) {
                    JsonArray sourcesArr = groupObj.getAsJsonArray(SOURCES);
                    for(JsonElement src: sourcesArr) {
                        JsonSourceParser srcParser = new JsonSourceParser(dir).parse(src);
                        sources.add(srcParser.getSource());
                    }
                } else {
                    throw new RuntimeException("Type of sources is erronous, expected array found " + groupObj.get(SOURCES));
                }
            }
            return this;
        } else {
            throw new RuntimeException("Type of group is erronous, expected object found " + element);
        }
    }
    
    public ArrayList<com.quew8.gutils.content.Source> getSources() {
        return sources;
    }
}
