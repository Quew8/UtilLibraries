package com.quew8.gutils.formatting;

import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 */
public class LinePrefixFormatter extends TextFormatter {
    private final String prefixFormat;
    private final int firstLineNumber;
    
    public LinePrefixFormatter(String prefixFormat, int firstLineNumber) {
        this.prefixFormat = prefixFormat;
        this.firstLineNumber = firstLineNumber;
    }
    
    public LinePrefixFormatter(String prefixFormat) {
        this(prefixFormat, 0);
    }
    
    @Override
    public void onLineIn(String in) {
        super.onLineIn(prefixFormat.replaceAll(Pattern.quote("$"), Integer.toString(getNLines() + firstLineNumber)) + in);
    }
    
}
