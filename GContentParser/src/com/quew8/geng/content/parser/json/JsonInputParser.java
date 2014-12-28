package com.quew8.geng.content.parser.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author Quew8
 */
class JsonInputParser {
    public static final String INDEX = "index", SOURCE = "source";
    
    private int index = -1;
    private String source;
    
    public JsonInputParser parse(JsonElement element) {
        if(element.isJsonObject()) {
            JsonObject inputObj = element.getAsJsonObject();
            index = JsonSourceSheetParser.getInteger(inputObj, INDEX, false, -1);
            source = JsonSourceSheetParser.getString(inputObj, SOURCE, true, null);
            return this;
        } else {
            throw new RuntimeException("Erronous type of input, expected object found " + element);
        }
    }
    
    public int getIndex() {
        return index;
    }
    
    public String getSource() {
        return source;
    }
}
