package com.quew8.gutils.desktop.windowing;

import com.quew8.gutils.desktop.LWJGLUtils;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * 
 * @author Quew8
 */
public class EmbeddedWindow extends EmbeddableSurface {
    private JFrame frame;
    
    public EmbeddedWindow(String title, final DisplayMode displayMode, int fps, boolean resizable) throws LWJGLException {
        super(displayMode, fps);
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                EmbeddedWindow.this.requestClose();
                super.windowClosing(e);
            }
        });
        frame.setTitle(title);
        frame.setLayout(new BorderLayout());
        frame.add(this);
        frame.setSize(displayMode.getWidth(), displayMode.getHeight());
        frame.setVisible(true);
        visible();
    }
    
    public EmbeddedWindow(String title, DisplayMode displayMode, int fps) throws LWJGLException {
        this(title, displayMode, fps, false);
    }
    
    public EmbeddedWindow(String title, int width, int height, int fps, boolean resizable) throws LWJGLException {
        this(title, LWJGLUtils.getDisplayMode(width, height), fps, resizable);
    }
    
    public EmbeddedWindow(String title, int width, int height, int fps) throws LWJGLException {
        this(title, LWJGLUtils.getDisplayMode(width, height), fps);
    }
    
    public EmbeddedWindow(String title, int fps, boolean resizable) throws LWJGLException {
        this(title, Display.getDesktopDisplayMode(), fps, resizable);
    }
    
    public EmbeddedWindow(String title, int fps) throws LWJGLException {
        this(title, Display.getDesktopDisplayMode(), fps);
    }
    
    @Override
    public void close() {
        super.close();
        frame.dispose();
    }
}
