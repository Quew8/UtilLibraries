package com.quew8.geng.rendering.modes;

import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public abstract class StaticRenderMode {

    public void onPreRendering() {};

    public void onPostRendering() {};

    public abstract void updateProjectionMatrix(FloatBuffer matrix);
}
