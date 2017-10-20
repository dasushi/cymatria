package com.app.sucrates.cymatria.util;

/**
 * Created by Stephen on 7/13/2017.
 */
public class MatrixHelper {

    /**
     * Creates 16-length perspective matrix in output[] using FOV and aspect info
     * @param output 16-length minimum float output array
     * @param yFovInDegrees Field of view for Y direction in degrees, as float
     * @param aspect aspect ratio, as float
     * @param start frustum start val / distance to near plane (aka n)
     *              Must be positive
     * @param end frustum end val / distance to far place (aka f),
     *            Must be positive and greater than start/n val
     */
    public static void perspectiveM(float[] output, float yFovInDegrees, float aspect, float start, float end) {
        final float angleRads = (float) (yFovInDegrees * Math.PI / 180.0);
        final float angle = (float) (1.0 / Math.tan(angleRads / 2.0));
        //set output values
        output[0] = angle / aspect;
        output[1] = 0f;
        output[2] = 0f;
        output[3] = 0f;
        output[4] = 0f;
        output[5] = angle;
        output[6] = 0f;
        output[7] = 0f;
        output[8] = 0f;
        output[9] = 0f;
        output[10] = -((end + start) / (end - start));
        output[11] = -1f;
        output[12] = 0f;
        output[13] = 0f;
        output[14] = -((2f * end * start) / (end - start));
        output[15] = 0f;
    }
}
