package com.quew8.gutils.desktop.opengl.bitmap;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.bitmapio.BitmapIOUtils;
import com.quew8.gutils.bitmapio.BitmapStruct;
import com.quew8.gutils.bitmapio.GroupedBitmapStruct;
import com.quew8.gutils.bitmapio.MappedBitmapStruct;
import com.quew8.gutils.opengl.GBuffer.GroupedGBuffer;
import com.quew8.gutils.opengl.GBuffer.MappedGroupedGBuffer;
import com.quew8.gutils.opengl.GBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import static com.quew8.gutils.desktop.opengl.DesktopOpenGL.*;

/**
 * 
 * @author Quew8
 */
public class BitmapUtils {
    private static int charWidth = 5, charHeight = 8;
    private static int charHGap = 1, charVGap = 1;
    private static Bitmap<MappedGroupedGBuffer<Character>> alphabet;
    
    private BitmapUtils() {
        
    }
    
    public static void setFont(Bitmap<MappedGroupedGBuffer<Character>> font) {
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        BitmapUtils.alphabet = font;
        BitmapUtils.charWidth = font.getWidth();
        BitmapUtils.charHeight = font.getHeight();
    }
    
    public static void loadFont(InputStream input) {
        setFont(readCharMappedBitmap(input));
    }
    
    public static void loadAlphabet() {
        alphabet = 
            makeMappedBitmap(new byte[][]{
                new byte[]{(byte)0x68, (byte)0x90, (byte)0xD0, (byte)0x30, (byte)0x90, (byte)0x60, (byte)0x00, (byte)0x00},//a
                new byte[]{(byte)0xB0, (byte)0x48, (byte)0x48, (byte)0x70, (byte)0x40, (byte)0xC0, (byte)0x00, (byte)0x00},//b
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x80, (byte)0x88, (byte)0x70, (byte)0x00, (byte)0x00, (byte)0x00},//c
                new byte[]{(byte)0x68, (byte)0x90, (byte)0x90, (byte)0x70, (byte)0x10, (byte)0x10, (byte)0x00, (byte)0x00},//d
                new byte[]{(byte)0x70, (byte)0x80, (byte)0xF8, (byte)0x88, (byte)0x70, (byte)0x00, (byte)0x00, (byte)0x00},//e
                new byte[]{(byte)0xF0, (byte)0x40, (byte)0x40, (byte)0xF0, (byte)0x40, (byte)0x30, (byte)0x00, (byte)0x00},//f
                new byte[]{(byte)0x60, (byte)0x10, (byte)0x70, (byte)0x90, (byte)0x98, (byte)0x68, (byte)0x00, (byte)0x00},//g
                new byte[]{(byte)0xC8, (byte)0x48, (byte)0x48, (byte)0x70, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x80},//h
                new byte[]{(byte)0x70, (byte)0x20, (byte)0x20, (byte)0x60, (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x00},//i
                new byte[]{(byte)0x60, (byte)0x90, (byte)0x10, (byte)0x10, (byte)0x30, (byte)0x00, (byte)0x10, (byte)0x00},//j
                new byte[]{(byte)0xC8, (byte)0x50, (byte)0x60, (byte)0x50, (byte)0x48, (byte)0x40, (byte)0xC0, (byte)0x00},//k
                new byte[]{(byte)0xE0, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0xC0, (byte)0x00},//l
                new byte[]{(byte)0xA8, (byte)0xA8, (byte)0xA8, (byte)0xA8, (byte)0x50, (byte)0x00, (byte)0x00, (byte)0x00},//m
                new byte[]{(byte)0xD8, (byte)0x50, (byte)0x50, (byte)0xF0, (byte)0x20, (byte)0x00, (byte)0x00, (byte)0x00},//n
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70, (byte)0x00, (byte)0x00, (byte)0x00},//o
                new byte[]{(byte)0xE0, (byte)0x40, (byte)0x60, (byte)0x50, (byte)0x50, (byte)0xA0, (byte)0x00, (byte)0x00},//p
                new byte[]{(byte)0x38, (byte)0x10, (byte)0x70, (byte)0x90, (byte)0x90, (byte)0x68, (byte)0x00, (byte)0x00},//q
                new byte[]{(byte)0xE0, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0xB0, (byte)0x00, (byte)0x00, (byte)0x00},//r
                new byte[]{(byte)0xE0, (byte)0x10, (byte)0x60, (byte)0x80, (byte)0x70, (byte)0x00, (byte)0x00, (byte)0x00},//s
                new byte[]{(byte)0x30, (byte)0x48, (byte)0x40, (byte)0x40, (byte)0xF0, (byte)0x40, (byte)0x00, (byte)0x00},//t
                new byte[]{(byte)0x68, (byte)0x90, (byte)0x90, (byte)0x90, (byte)0x90, (byte)0x00, (byte)0x00, (byte)0x00},//u
                new byte[]{(byte)0x20, (byte)0x70, (byte)0x50, (byte)0x50, (byte)0xD8, (byte)0x00, (byte)0x00, (byte)0x00},//v
                new byte[]{(byte)0x50, (byte)0xA8, (byte)0xA8, (byte)0xA8, (byte)0x88, (byte)0x00, (byte)0x00, (byte)0x00},//w
                new byte[]{(byte)0x88, (byte)0x50, (byte)0x70, (byte)0x50, (byte)0x88, (byte)0x00, (byte)0x00, (byte)0x00},//x
                new byte[]{(byte)0x60, (byte)0x20, (byte)0x20, (byte)0x50, (byte)0x88, (byte)0x00, (byte)0x00, (byte)0x00},//y
                new byte[]{(byte)0xF8, (byte)0x80, (byte)0x70, (byte)0x08, (byte)0xF8, (byte)0x00, (byte)0x00, (byte)0x00},//z
                
                new byte[]{(byte)0x88, (byte)0x88, (byte)0x88, (byte)0xF8, (byte)0x88, (byte)0x88, (byte)0x50, (byte)0x20},//A
                new byte[]{(byte)0xF0, (byte)0x88, (byte)0x88, (byte)0xF0, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0xF0},//B
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x88, (byte)0x70},//C
                new byte[]{(byte)0xE0, (byte)0x90, (byte)0x90, (byte)0x88, (byte)0x88, (byte)0x90, (byte)0x90, (byte)0xE0},//D
                new byte[]{(byte)0xF8, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0xF0, (byte)0x80, (byte)0x80, (byte)0xF8},//E
                new byte[]{(byte)0x80, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0xF0, (byte)0x80, (byte)0x80, (byte)0xF8},//F
                new byte[]{(byte)0x60, (byte)0x90, (byte)0x90, (byte)0xB8, (byte)0x80, (byte)0x80, (byte)0x88, (byte)0x70},//G
                new byte[]{(byte)0x88, (byte)0x88, (byte)0x88, (byte)0xF8, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88},//H
                new byte[]{(byte)0xF8, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0xF8},//I
                new byte[]{(byte)0x40, (byte)0xA0, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0xF8},//J
                new byte[]{(byte)0x88, (byte)0x88, (byte)0x90, (byte)0xE0, (byte)0xE0, (byte)0xB0, (byte)0x98, (byte)0x88},//K
                new byte[]{(byte)0xF8, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x80},//L
                new byte[]{(byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0xA8, (byte)0xA8, (byte)0x50, (byte)0x50},//M
                new byte[]{(byte)0x88, (byte)0x98, (byte)0x98, (byte)0xA8, (byte)0xA8, (byte)0xC8, (byte)0xC8, (byte)0x88},//N
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70},//O
                new byte[]{(byte)0x80, (byte)0x80, (byte)0x80, (byte)0xF0, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70},//P
                new byte[]{(byte)0x68, (byte)0x90, (byte)0xA8, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70},//Q
                new byte[]{(byte)0x88, (byte)0x90, (byte)0xA0, (byte)0xF0, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70},//R
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x08, (byte)0x30, (byte)0x60, (byte)0x80, (byte)0x88, (byte)0x70},//S
                new byte[]{(byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0xF8},//T
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88},//U
                new byte[]{(byte)0x20, (byte)0x70, (byte)0x50, (byte)0x50, (byte)0xD8, (byte)0x88, (byte)0x88, (byte)0x88},//V
                new byte[]{(byte)0x50, (byte)0x50, (byte)0x50, (byte)0xA8, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88},//W
                new byte[]{(byte)0x88, (byte)0x88, (byte)0x50, (byte)0x20, (byte)0x20, (byte)0x50, (byte)0x88, (byte)0x88},//X
                new byte[]{(byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x50, (byte)0x50, (byte)0x88, (byte)0x88},//Y
                new byte[]{(byte)0xF8, (byte)0x80, (byte)0x40, (byte)0x20, (byte)0x20, (byte)0x10, (byte)0x08, (byte)0xF8},//Z
                
                new byte[]{(byte)0x70, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x60, (byte)0x20},//1
                new byte[]{(byte)0xF8, (byte)0x80, (byte)0x40, (byte)0x20, (byte)0x10, (byte)0x88, (byte)0x88, (byte)0x70},//2
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x08, (byte)0x30, (byte)0x08, (byte)0x08, (byte)0x88, (byte)0x70},//3
                new byte[]{(byte)0x10, (byte)0x10, (byte)0x78, (byte)0x50, (byte)0x50, (byte)0x40, (byte)0x40, (byte)0x40},//4
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x08, (byte)0x08, (byte)0xF0, (byte)0x80, (byte)0x80, (byte)0xF8},//5
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70, (byte)0x80, (byte)0x88, (byte)0x70},//6
                new byte[]{(byte)0x20, (byte)0x20, (byte)0x20, (byte)0x10, (byte)0x10, (byte)0x08, (byte)0x08, (byte)0xF8},//7
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70, (byte)0x88, (byte)0x88, (byte)0x70},//8
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x08, (byte)0x70, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70},//9
                new byte[]{(byte)0x70, (byte)0x88, (byte)0x98, (byte)0xA8, (byte)0xA8, (byte)0xC8, (byte)0x88, (byte)0x70},//0
                
                new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},//' '
                new byte[]{(byte)0x00, (byte)0x20, (byte)0x20, (byte)0xF8, (byte)0x20, (byte)0x20, (byte)0x00, (byte)0x00},//+
                new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0xF8, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},//-
                new byte[]{(byte)0x00, (byte)0x00, (byte)0xF8, (byte)0x00, (byte)0xF8, (byte)0x00, (byte)0x00, (byte)0x00},//=
                new byte[]{(byte)0x70, (byte)0x70, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},//.
                new byte[]{(byte)0xF8, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},//_
                new byte[]{(byte)0x10, (byte)0x20, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x20, (byte)0x10},//(
                new byte[]{(byte)0x40, (byte)0x20, (byte)0x10, (byte)0x10, (byte)0x10, (byte)0x10, (byte)0x20, (byte)0x40},//)
                new byte[]{(byte)0x70, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x70},//[
                new byte[]{(byte)0x70, (byte)0x10, (byte)0x10, (byte)0x10, (byte)0x10, (byte)0x10, (byte)0x10, (byte)0x70},//]
                new byte[]{(byte)0x10, (byte)0x20, (byte)0x20, (byte)0x40, (byte)0x20, (byte)0x20, (byte)0x10, (byte)0x00},//{
                new byte[]{(byte)0x40, (byte)0x20, (byte)0x20, (byte)0x10, (byte)0x20, (byte)0x20, (byte)0x40, (byte)0x00},//}
                
                new byte[]{(byte)0x20, (byte)0x00, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20},//!
                new byte[]{(byte)0xF8, (byte)0x40, (byte)0x40, (byte)0xF8, (byte)0x40, (byte)0x40, (byte)0x48, (byte)0x70},//£
                new byte[]{(byte)0x20, (byte)0x70, (byte)0xA8, (byte)0x30, (byte)0x60, (byte)0xA8, (byte)0x70, (byte)0x20},//$
                new byte[]{(byte)0x00, (byte)0x98, (byte)0xD8, (byte)0x60, (byte)0x30, (byte)0xD8, (byte)0xC8, (byte)0x00},//%
                new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x88, (byte)0x50, (byte)0x20},//^
                new byte[]{(byte)0x68, (byte)0x90, (byte)0x58, (byte)0x20, (byte)0x50, (byte)0x88, (byte)0x88, (byte)0x70},//&
                new byte[]{(byte)0x00, (byte)0x00, (byte)0x20, (byte)0xA8, (byte)0x78, (byte)0x70, (byte)0xA8, (byte)0x20},//*
                new byte[]{(byte)0x60, (byte)0x20, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x20, (byte)0x20, (byte)0x00},//;
                new byte[]{(byte)0x00, (byte)0x20, (byte)0x20, (byte)0x00, (byte)0x00, (byte)0x20, (byte)0x20, (byte)0x00},//:
                new byte[]{(byte)0x60, (byte)0x20, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},//,
                new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x50, (byte)0x50, (byte)0x00},//'"'
                new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x20, (byte)0x20, (byte)0x00},//"'"
                new byte[]{(byte)0x60, (byte)0x80, (byte)0x98, (byte)0xA8, (byte)0xA8, (byte)0x98, (byte)0x88, (byte)0x70},//@
                new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB0, (byte)0x68, (byte)0x00, (byte)0x00, (byte)0x00},//~
                new byte[]{(byte)0xA0, (byte)0x50, (byte)0xF8, (byte)0x50, (byte)0x50, (byte)0xF8, (byte)0x50, (byte)0x28},//#
                new byte[]{(byte)0x80, (byte)0xC0, (byte)0x40, (byte)0x60, (byte)0x30, (byte)0x10, (byte)0x18, (byte)0x08},//'/'
                new byte[]{(byte)0x08, (byte)0x18, (byte)0x10, (byte)0x30, (byte)0x60, (byte)0x40, (byte)0xC0, (byte)0x80},//'\'
                new byte[]{(byte)0x60, (byte)0x00, (byte)0x20, (byte)0x30, (byte)0x10, (byte)0x08, (byte)0x90, (byte)0x60},//?
                new byte[]{(byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20},//'|'
                new byte[]{(byte)0x00, (byte)0xC0, (byte)0x30, (byte)0x08, (byte)0x30, (byte)0xC0, (byte)0x00, (byte)0x00},//>
                new byte[]{(byte)0x00, (byte)0x18, (byte)0x60, (byte)0x80, (byte)0x60, (byte)0x18, (byte)0x00, (byte)0x00},//<
                
            },
            new Character[] {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                ' ', '+', '-', '=', '.', '_', '(', ')', '[', ']', '{', '}',
                '!', '£', '$', '%', '^', '&', '*', ';', ':', ',', '"', '\'', '@','~', '#', '/', '\\', '?', '|', '>', '<',
            },
            charWidth, charHeight);
    }
    
    public static void deleteAlphabet() {
        alphabet.delete();
    }
    
    public static void setCharGaps(int hGap, int vGap) {
        BitmapUtils.charHGap = hGap;
        BitmapUtils.charVGap = vGap;
    }

    public static int getCharWidth() {
        return charWidth;
    }

    public static int getCharHeight() {
        return charHeight;
    }

    public static int getCharHGap() {
        return charHGap;
    }

    public static int getCharVGap() {
        return charVGap;
    }
    
    public static int getCharWidthWithGap() {
        return getCharWidth() + getCharHGap();
    }
    
    public static int getCharHeightWithGap() {
        return getCharHeight() + getCharVGap();
    }
    
    public static int[] getRequiredDimsOfText(String s) {
        String[] lines  = prepareText(s).split("[\n]", -1);
        int maxLineLength = lines[0].length();
        for(int i = 1; i < lines.length; i++) {
            if(lines[i].length() > maxLineLength) {
                maxLineLength = lines[i].length();
            }
        }
        int width = ((maxLineLength - 1) * getCharWidthWithGap()) + getCharWidth();
        int height = ((lines.length - 1) * getCharHeightWithGap()) + getCharHeight();
        return new int[]{width, height};
    }
    
    private static String prepareText(String text) {
        return text.replace("\u0009", "    ");
    }
    
    public static void drawText(String s, float x, float y, float z, BitmapDrawCallback callback) {
        String[] lines  = prepareText(s).split("[\n]", -1);
        alphabet.bind();
        for(int i = 0; i < lines.length; i++) {
            glRasterPos3f(x, y, z);
            glBitmap(0, 0, 0, 0, 0, getCharHeightWithGap() * (-i) , 0);
            for(int k = 0; k < lines[i].length(); k++) {
                callback.onPreDraw(i, k);
                glBitmap(charWidth, charHeight, 0, 0, getCharWidthWithGap(), 0, alphabet.getBuffer().getOffsetOfElement(lines[i].charAt(k)));
            }
        }
        alphabet.unbind();
    }
    
    public static void drawText(String s, float x, float y, float z) {
        drawText(s, x, y, z, new BitmapDrawCallback());
    }
    
    public static void drawLine(String s, float x, float y, float z, BitmapDrawCallback callback) {
        callback.onPreDraw(0, 0);
        glRasterPos3f(x, y, z);
        alphabet.bind();
        s = prepareText(s);
        for(int i = 0; i < s.length(); i++) {
            if(alphabet.getBuffer().getOffsetOfElement((s.charAt(i))) < 0) {
                System.out.println("Cannot Find '" + s.charAt(i) + "' Value = " + ((int)s.charAt(i)));
            }
            callback.onPreDraw(0, i + 1);
            glBitmap(charWidth, charHeight, 0, 0, getCharWidthWithGap(), 0, alphabet.getBuffer().getOffsetOfElement((s.charAt(i))));
        }
        alphabet.unbind();
    }
    
    public static void drawLine(String s, float x, float y, float z) {
        drawLine(s, x, y, z, new BitmapDrawCallback());
    }
    
    public static void drawChar(char c, float x, float y, float z) {
        glRasterPos3f(x, y, z);
        alphabet.bind();
        glBitmap(charWidth, charHeight, 0, 0, 0, 0, alphabet.getBuffer().getOffsetOfElement(c));
        alphabet.unbind();
    }
    
    public static void drawBitmap(Bitmap<?> bitmap, float x, float y, float z) {
        bitmap.bind();
        glRasterPos3f(x, y, z);
        glBitmap(bitmap.getWidth(), bitmap.getHeight(), 0, 0, 0, 0, 0);
        bitmap.unbind();
    }
    
    public static void drawBitmap(Bitmap<GroupedGBuffer> bitmap, int index, float x, float y, float z) {
        bitmap.bind();
        glRasterPos3f(x, y, z);
        glBitmap(bitmap.getWidth(), bitmap.getHeight(), 0, 0, 0, 0, bitmap.getBuffer().getOffsetOf(index));
        bitmap.unbind();
    }
    
    public static <E> void drawBitmap(Bitmap<MappedGroupedGBuffer<E>> bitmap, E element, float x, float y, float z) {
        bitmap.bind();
        glRasterPos3f(x, y, z);
        glBitmap(bitmap.getWidth(), bitmap.getHeight(), 0, 0, 0, 0, bitmap.getBuffer().getOffsetOfElement(element));
        bitmap.unbind();
    }
    
    public static Bitmap<GBuffer> readBitmap(InputStream input) {
        try {
            BitmapStruct struct = BitmapIOUtils.readBitmap(input);
            return makeBitmap(struct.data, struct.width, struct.height);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static Bitmap<GBuffer> makeBitmap(ByteBuffer data, int width, int height) {
        return new Bitmap<>(new GBuffer(data, GL_PIXEL_UNPACK_BUFFER, GL_STATIC_DRAW), width, height);
    }
    
    public static Bitmap<GBuffer> makeBitmap(byte[] data, int width, int height) {
        return new Bitmap<>(new GBuffer(BufferUtils.createByteBuffer(data), GL_PIXEL_UNPACK_BUFFER, GL_STATIC_DRAW), width, height);
    }
    
    public static Bitmap<GroupedGBuffer> readGroupedBitmap(InputStream input) {
        try {
            GroupedBitmapStruct struct = BitmapIOUtils.readGroupedBitmap(input);
            return makeGroupedBitmap(struct.data, struct.width, struct.height);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static Bitmap<GroupedGBuffer> makeGroupedBitmap(ByteBuffer data, int dataSize, int width, int height) {
        return new Bitmap<>(new GroupedGBuffer(data, dataSize, (byte)1, GL_PIXEL_UNPACK_BUFFER, GL_STATIC_DRAW), width, height);
    }
    
    public static Bitmap<GroupedGBuffer> makeGroupedBitmap(byte[][] data, int width, int height) {
        return new Bitmap<>(new GroupedGBuffer(data, GL_PIXEL_UNPACK_BUFFER, GL_STATIC_DRAW), width, height);
    }
    
    public static Bitmap<MappedGroupedGBuffer<Character>> readCharMappedBitmap(InputStream input) {
        try {
            MappedBitmapStruct<Character> struct = 
                    BitmapIOUtils.readMappedBitmap(input, BitmapIOUtils.CHAR_IO, new Character[]{});
            return makeMappedBitmap(struct.data, struct.mapping, struct.width, struct.height);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static <E> Bitmap<MappedGroupedGBuffer<E>> makeMappedBitmap(ByteBuffer data, int dataSize, E[] mapping, int width, int height) {
        return new Bitmap<>(new MappedGroupedGBuffer<>(data, dataSize, (byte)1, mapping, GL_PIXEL_UNPACK_BUFFER, GL_STATIC_DRAW), width, height);
    }
    
    public static <E> Bitmap<MappedGroupedGBuffer<E>> makeMappedBitmap(byte[][] data, E[] mapping, int width, int height) {
        return new Bitmap<>(new MappedGroupedGBuffer<>(data, mapping, GL_PIXEL_UNPACK_BUFFER, GL_STATIC_DRAW), width, height);
    }
    
    public static class BitmapDrawCallback {
        public void onPreDraw(int line, int index) {}
    }
}
