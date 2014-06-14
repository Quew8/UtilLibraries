package com.quew8.geng.geometry;

import com.quew8.gmath.Matrix;
import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.opengl.shaders.ShaderUtils;
import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public class Skeleton {
    private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    private final Matrix bindShapeMatrix;
    private final Joint[] joints;
    private final int nJoints;
    
    public Skeleton(Matrix bindShapeMatrix, Joint[] joints, int nJoints) {
        this.bindShapeMatrix = bindShapeMatrix;
        this.joints = joints;
        this.nJoints = nJoints;
    }
    
    public int getNJoints() {
        return nJoints;
    }
    
    public void uploadSkeleton(int programId, String bsmVar, String ibmVar, String jmVar) {
        bindShapeMatrix.putIn(matrixBuffer);
        ShaderUtils.setUniformMatrix(programId, bsmVar, matrixBuffer);
        for(int i = 0; i < joints.length; i++) {
            joints[i].uploadJoint(programId, ibmVar, jmVar, matrixBuffer);
        }
    }
}
