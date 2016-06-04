package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import com.quew8.gutils.ArrayUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
abstract class CollParseUtils {
    private static final String SID = "sid";
    
    protected CollParseUtils() {
        
    }
    
    protected static float[] parseVec4(String text) {
        Float[] data = new Float[4];
        parseFloatsInto(text, data, 0, 4);
        return ArrayUtils.unbox(data);
    }
    
    protected static Vector parseVec3(String text) {
        Float[] data = new Float[3];
        parseFloatsInto(text, data, 0, 3);
        return new Vector(data[0], data[1], data[2]);
    }
    
    protected static Matrix parseMatrix(String text) {
        Float[] data = new Float[16];
        parseFloatsInto(text, data, 0, 16);
        Matrix m = new Matrix();
        m.setDataFromCM(ArrayUtils.unbox(data), 0);
        return m;
    }
    
    protected static void parseFloatsInto(String text, Float[] into, int offset, int n) {
        Pattern floatPattern = Pattern.compile("[+-]?[\\d]+(\\.[\\d]+)?([e][+-]?[\\d]+)?");
        Matcher matcher = floatPattern.matcher(text);
        int i = 0;
        for(; matcher.find() && n > i; i++) {
            into[i + offset] = Float.parseFloat(matcher.group());
        }
        if(n > i) {
            throw new RuntimeException("Not Enough Floats Found: " + n + " required, " + i + " found");
        }
    }
    
    protected static void parseUIntsInto(String text, int[] into, int offset, int n) {
        Pattern floatPattern = Pattern.compile("[\\d]+");
        Matcher matcher = floatPattern.matcher(text);
        int i = 0;
        for(; matcher.find() && n > i; i++) {
            into[offset + i] = Integer.parseInt(matcher.group());
        }
        if(n > i) {
            throw new RuntimeException("Not Enough Ints Found: " + n + " required, " + i + " found");
        }
    }
    
    protected static void parseStringsInto(String text, String[] into, int offset, int n) {
        Pattern floatPattern = Pattern.compile("[a-zA-Z_]+");
        Matcher matcher = floatPattern.matcher(text);
        int i = 0;
        for(; matcher.find() && n > i; i++) {
            into[offset + i] = matcher.group();
        }
        if(n > i) {
            throw new RuntimeException("Not Enough Strings Found: " + n + " required, " + i + " found");
        }
    }
    
    public static Element findTargetWithSID(Element root, String targetSID) {
        Element elem = findTargetUpWithSID(root, targetSID, null);
        if(elem == null) {
            throw new XMLParseException("No element found with sid \"" + targetSID + "\"");
        }
        return elem;
    }

    private static Element findTargetUpWithSID(Element root, String targetSID, Element excludeChild) {
        for(Element child: root.elements()) {
            String childSID = child.attributeValue(SID);
            if(childSID != null && childSID.equals(targetSID)) {
                return child;
            }
        }
        for(Element child: root.elements()) {
            if(child != excludeChild) {
                Element result = findTargetDownWithSID(child, targetSID);
                if(result != null) {
                    return result;
                }
            }
        }
        Element parent = root.getParent();
        if(parent != null) {
            return findTargetUpWithSID(parent, targetSID, root);
        } else {
            return null;
        }
    }
    
    private static Element findTargetDownWithSID(Element root, String targetSID) {
        for(Element child: root.elements()) {
            String childSID = child.attributeValue(SID);
            if(childSID != null && childSID.equals(targetSID)) {
                return child;
            }
        }
        for(Element child: root.elements()) {
            Element result = findTargetDownWithSID(child, targetSID);
            if(result != null) {
                return result;
            }
        }
        return null;
    }
}
