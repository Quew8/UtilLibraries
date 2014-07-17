package com.quew8.geng.content;

import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.content.SourceSheet;
import java.net.URL;
/**
 *
 * @author Quew8
 */
public class URLReader implements ContentReader<URL> {
    
    @Override
    public URL read(Source in) {
        return in.getURL();
    }
    
}
