package com.app.sucrates.cymatria.objects;

import com.app.sucrates.cymatria.data.VertexArray;
import com.app.sucrates.cymatria.ogl.TextureShaderProgram;
import com.app.sucrates.cymatria.util.Constants;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by Stephen on 7/18/2017.
 */
public class Table {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORD_COUNT = 2;
    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + TEXTURE_COORD_COUNT) * Constants.BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            //X,        Y,      S,      T
            //triangle fan
            0f,         0f,     0.5f,   0.5f,
            -0.5f,      -0.5f,  0f,     0.9f,
            0.5f,       -0.5f,  1f,     0.9f,
            0.5f,       0.5f,   1f,     0.1f,
            -0.5f,      0.5f,   0f,     0.1f,
            -0.5f,      -0.5f,  0f,     0.9f
    };

    private final VertexArray vertexArray;

    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bind(TextureShaderProgram textureProgram) {

        vertexArray.setVertexAttributePointer(
                0,
                textureProgram.getPositionAttribLocation(),
                POSITION_COMPONENT_COUNT, STRIDE);

        vertexArray.setVertexAttributePointer(
                POSITION_COMPONENT_COUNT,
                textureProgram.getTextureCoordAttribLocation(),
                POSITION_COMPONENT_COUNT, STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }

}
