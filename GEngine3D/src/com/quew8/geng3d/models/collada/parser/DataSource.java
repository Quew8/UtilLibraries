package com.quew8.geng3d.models.collada.parser;

/**
 *
 * @author Quew8
 */
public interface DataSource {
    public void putData(Object[] in, int offset, int[] indices);
    public void putData(Object[] in, int offset, int n);
    public int getNValuesPerVertex();
    public int getCount();
}
