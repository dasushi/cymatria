package com.app.sucrates.cymatria.ogl;

import android.content.Context;

import com.app.sucrates.cymatria.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by Stephen on 7/18/2017.
 */
public class ColorShaderProgram extends ShaderProgram {

    //uniform locations
    private final int uMatrixLocation;
    private final int uColorLocation;

    //attribute locations
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.vertex_shader, R.raw.frag_shader);

        //fetch uniform and attribute locations
        uMatrixLocation = glGetUniformLocation(programID, U_MATRIX);
        uColorLocation = glGetUniformLocation(programID, U_COLOR);
        aPositionLocation = glGetAttribLocation(programID, A_POSITION);
        aColorLocation = glGetAttribLocation(programID, A_COLOR);

    }

    public void setUniforms(float[] matrix, float r, float g, float b) {
        //pass matrix into shader program
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        glUniform4f(uColorLocation, r, g, b, 1f);
    }

    public int getPositionAttribLocation() {
        return aPositionLocation;
    }

    public int getColorAttribLocation() {
        return aColorLocation;
    }
}
