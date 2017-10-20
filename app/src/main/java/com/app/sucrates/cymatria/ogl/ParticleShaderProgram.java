package com.app.sucrates.cymatria.ogl;

import android.content.Context;

import com.app.sucrates.cymatria.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by Stephen on 9/8/2017.
 */
public class ParticleShaderProgram extends ShaderProgram {

    //uniform locations
    private final int uMatrixLocation;
    private final int uTimeLocation;
    private final int uFFTLocation;

    //attribute locations
    private final int aPositionLocation;
    private final int aColorLocation;
    private final int aDirectionVectorLocation;
    private final int aParticleStartTimeLocation;


    public ParticleShaderProgram(Context context) {
        super(context, R.raw.particle_vertex_shader, R.raw.particle_fragment_shader);

        //retrieve uniform locations for shader program
        uMatrixLocation = glGetUniformLocation(programID, U_MATRIX);
        uTimeLocation = glGetUniformLocation(programID, U_TIME);
        uFFTLocation = glGetUniformLocation(programID, U_FFT);

        //retrieve attrib locations for shader program
        aPositionLocation = glGetAttribLocation(programID, A_POSITION);
        aColorLocation = glGetAttribLocation(programID, A_COLOR);
        aDirectionVectorLocation = glGetAttribLocation(programID, A_DIRECTION_VECTOR);
        aParticleStartTimeLocation = glGetAttribLocation(programID, A_PARTICLE_START_TIME);
    }

    public void setUniforms(float[] matrix, float elapsedTime){//, float[] fftMatrix) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        //glUniformMatrix4fv(uFFTLocation, 1, false, fftMatrix, 0);
        glUniform1f(uTimeLocation, elapsedTime);
    }

    public int getPositionAttribLocation() {
        return aPositionLocation;
    }

    public int getColorAttribLocation() {
        return aColorLocation;
    }

    public int getDirectionVectorAttribLocation() {
        return aDirectionVectorLocation;
    }

    public int getParticleStartTimeAttribLocation() {
        return aParticleStartTimeLocation;
    }

}
