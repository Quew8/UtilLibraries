package com.quew8.geng.content.parser;

import com.quew8.geng.content.parser.xml.XMLSourceSheetParser;
import com.quew8.geng.content.parser.json.JsonSourceSheetParser;
import com.quew8.gutils.FileUtils;
import com.quew8.gutils.ResourceLoader;

/**
 *
 * @author Quew8
 */
public class SourceSheetParserUtils {
    
    public static SourceSheetParser readSourceSheet(String resource, ResourceLoader loader) {
        switch(FileUtils.getExtension(resource)) {
            case "xml": {
                SourceSheetParser parser = new XMLSourceSheetParser();
                parser.read(resource, loader);
                return parser;
            }
            case "json": {
                SourceSheetParser parser = new JsonSourceSheetParser();
                parser.read(resource, loader);
                return parser;
            }
            default: {
                throw new IllegalArgumentException("Unrecognized source sheet file extension \"" + FileUtils.getExtension(resource) + "\" expected xml or json");
            }
        }
    }
    
    public static SourceSheetParser readSourceSheet(String resource) {
        return readSourceSheet(resource, ResourceLoader.INTERNAL);
    }
}
