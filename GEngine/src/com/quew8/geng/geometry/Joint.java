package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix;
import com.quew8.gutils.opengl.shaders.ShaderUtils;
import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public class Joint {
    private final int index;
    private final String indexString;
    private final Matrix invBindMatrix;
    private final Matrix jointMatrix;
    private final Matrix tempMatrix = new Matrix();
    private final Joint[] children;
    
    public Joint(int index, Matrix invBindMatrix, Matrix jointMatrix, Joint[] children) {
        this.index = index;
        this.indexString = "[" + Integer.toString(index) + "]";
        this.invBindMatrix = invBindMatrix;
        this.jointMatrix = jointMatrix;
        this.children = children;
    }
    
    public void uploadJoint(int programId, String ibmVar, String jmVar, Matrix parentWJM, FloatBuffer matrixBuffer) {
        Matrix.times(tempMatrix, parentWJM, jointMatrix);
        matrixBuffer.put(tempMatrix.getData());
        matrixBuffer.flip();
        ShaderUtils.setUniformMatrix(programId, jmVar + indexString, matrixBuffer);
        matrixBuffer.put(invBindMatrix.getData());
        matrixBuffer.flip();
        ShaderUtils.setUniformMatrix(programId, ibmVar + indexString, matrixBuffer);
        for(int i = 0; i < children.length; i++) {
            children[i].uploadJoint(programId, ibmVar, jmVar, tempMatrix, matrixBuffer);
        }
    }
    
    public void uploadJoint(int programId, String ibmVar, String jmVar, FloatBuffer matrixBuffer) {
        uploadJoint(programId, ibmVar, jmVar, new Matrix(), matrixBuffer);
    }
}
