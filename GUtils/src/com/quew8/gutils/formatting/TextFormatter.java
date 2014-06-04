package com.quew8.gutils.formatting;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Quew8
 */
public class TextFormatter implements Iterable<String> {
    private final ArrayList<String> lines = new ArrayList<>();
    
    public void readIn(String text) {
        String[] linesIn = text.split("[\n\r]");
        for(int i = 0; i < linesIn.length; i++) {
            onLineIn(linesIn[i]);
        }
    }
    
    protected void addLine(String line) {
        lines.add(line);
    }
    
    public String[] getLines() {
        return lines.toArray(new String[lines.size()]);
    }
    
    public int getNLines() {
        return lines.size();
    }
    
    public String getText() {
        if(lines.isEmpty()) {
            return "";
        }
        String text = lines.get(0);
        for (int i = 1; i < lines.size(); i++) {
            text += "\n" + lines.get(i);
        }
        return text;
    }
    
    public void onPreParse() {}
    
    public void onLineIn(String in) {
        addLine(in);
    }
    
    public void onPostParse() {}
    
    @Override
    public Iterator<String> iterator() {
        return lines.iterator();
    }
}
