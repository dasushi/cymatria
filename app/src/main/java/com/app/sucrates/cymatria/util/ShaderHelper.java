package com.app.sucrates.cymatria.util;

import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

/**
 * Created by Stephen on 7/11/2017.
 */
public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderVal){
        return compileShader(GL_VERTEX_SHADER, shaderVal);
    }

    public static int compileFragmentShader(String shaderVal){
        return compileShader(GL_FRAGMENT_SHADER, shaderVal);
    }

    private static int compileShader(int shaderType, String shaderVal){
        final int shaderId = glCreateShader(shaderType);

        if(shaderId == 0) {
            if(LoggerConfig.ENABLED){
                Log.w(TAG, "Error compiling new shader");
            }

            return 0;
        }
        glShaderSource(shaderId, shaderVal);
        glCompileShader(shaderId);

        final int[] shaderStatus = new int[1];
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, shaderStatus, 0);

        //log verbose result
        if(LoggerConfig.ENABLED) {
            Log.v(TAG, "Shader Compilation Results:\n"
                    + shaderVal + "\n"
                    + glGetShaderInfoLog(shaderId));
        }
        //check if status failed
        if(shaderStatus[0] == 0) {
            glDeleteShader(shaderId);
            if(LoggerConfig.ENABLED) {
                Log.w(TAG, "Shader Compilation failed: " + shaderVal);
            }
            return 0;
        }
        return shaderId;
    }

    public static int linkProgram(int vertexId, int fragId) {
        final int programId = glCreateProgram();

        if(programId == 0){
            if(LoggerConfig.ENABLED){
                Log.w(TAG, "Could not create OpenGL Program");
            }
            return 0;
        }
        //attach both shaders
        glAttachShader(programId, vertexId);
        glAttachShader(programId, fragId);

        glLinkProgram(programId);

        //check link result
        final int[] linkStatus = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);

        if(LoggerConfig.ENABLED) {
            Log.v(TAG, "Program Linking Results for ID" + programId + " with LinkStatus " + linkStatus[0] + ":\n" + glGetProgramInfoLog(programId));
        }
        if(linkStatus[0] == 0) {
            //handle failure
            glDeleteProgram(programId);
            if(LoggerConfig.ENABLED){
                Log.w(TAG, "Linking program failed.");
                return 0;
            }
        }

        return programId;
    }

    public static boolean validateProgram(int programId) {
        glValidateProgram(programId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Validate Program Results: " + validateStatus[0]
                + "\nLog: " + glGetProgramInfoLog(programId));

        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderVal, String fragShaderVal) {
        int programID;

        int vertexShader = compileVertexShader(vertexShaderVal);
        int fragShader = compileFragmentShader(fragShaderVal);

        programID = linkProgram(vertexShader, fragShader);

        if(LoggerConfig.ENABLED) {
            validateProgram(programID);
        }

        return programID;
    }
}
