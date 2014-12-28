package com.quew8.geng.content.parser;

import com.quew8.gutils.ResourceLoader;
import com.quew8.gutils.content.SourceSheet;

/**
 *
 * @author Quew8
 */
public interface SourceSheetParser {
    
    public void read(String resource, ResourceLoader resourceLoader);
    
    public SourceSheet getSourceSheet() throws ClassNotFoundException;
    
    public com.quew8.gutils.content.Source[] getSources();
    
    public com.quew8.gutils.content.Source getSource(int i);
    
    public int getNSources();
    
    public String getIdClassName();
    
    public String getReaderClassName();
}
