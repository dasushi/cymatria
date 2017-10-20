package com.app.sucrates.cymatria.ogl;

import android.content.Context;

import com.app.sucrates.cymatria.R;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by Stephen on 7/18/2017.
 */
public class TextureShaderProgram extends ShaderProgram{

    //uniform locations
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    //attribute locations
    private final int aPositionLocation;
    private final int aTextureCoordLocation;

    public TextureShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader, R.raw.texture_frag_shader);
        //fetch uniform and attribute locations
        uMatrixLocation = glGetUniformLocation(programID, U_MATRIX);
        uTextureUnitLocation = glGetUniformLocation(programID, U_TEXTURE_UNIT);
        aPositionLocation = glGetAttribLocation(programID, A_POSITION);
        aTextureCoordLocation = glGetAttribLocation(programID, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureID) {
        //pass matrix into shader program
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        //set active texture unit to unit 0
        glActiveTexture(GL_TEXTURE0);
        //bind texture to selected unit
        glBindTexture(GL_TEXTURE_2D, textureID);

        //set texture uniform sampler to use texture unit 0
        glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionAttribLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordAttribLocation() {
        return aTextureCoordLocation;
    }
}
