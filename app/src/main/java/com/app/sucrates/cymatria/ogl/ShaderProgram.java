package com.app.sucrates.cymatria.ogl;

import android.content.Context;

import com.app.sucrates.cymatria.util.ShaderHelper;
import com.app.sucrates.cymatria.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by Stephen on 7/18/2017.
 */
public class ShaderProgram {

    //uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";
    protected static final String U_TIME = "u_Time";
    protected static final String U_FFT = "u_FFT";

    //attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    protected static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";

    protected final int programID;

    protected ShaderProgram(Context context, int vertexShaderResourceID, int fragShaderResourceID) {
        final String vertexVal =
                TextResourceReader.readTextFileFromResource(context, vertexShaderResourceID);
        final String fragVal =
                TextResourceReader.readTextFileFromResource(context, fragShaderResourceID);
        programID = ShaderHelper.buildProgram(vertexVal, fragVal);
    }

    public void useProgram() {
        glUseProgram(programID);
    }

}
