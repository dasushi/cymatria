package com.app.sucrates.cymatria;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;

import com.app.sucrates.cymatria.objects.ParticleSource;
import com.app.sucrates.cymatria.objects.ParticleSystem;
import com.app.sucrates.cymatria.objects.Table;
import com.app.sucrates.cymatria.ogl.ParticleShaderProgram;
import com.app.sucrates.cymatria.ogl.TextureShaderProgram;
import com.app.sucrates.cymatria.util.Geometry;
import com.app.sucrates.cymatria.util.MatrixHelper;
import com.app.sucrates.cymatria.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;

/**
 * Created by Stephen on 9/8/2017.
 */
public class ParticlesRenderer implements GLSurfaceView.Renderer{

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    private final float angleVarDegrees = 5f;
    private final float speedVar = 1f;
    //private float[] fftMatrix = new float[4];

    private ParticleShaderProgram particleProgram;
    private ParticleSystem particleSystem;

    private ParticleSource redSource;
    private ParticleSource greenSource;
    private ParticleSource blueSource;

    private Table table;
    private TextureShaderProgram textureProgram;
    private int textureID;

    private long globalStartTime;

    public ParticlesRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        //enable additive blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);

        textureProgram = new TextureShaderProgram(context);
        textureID = TextureHelper.loadTexture(context, R.drawable.plate);

        particleProgram = new ParticleShaderProgram(context);
        particleSystem = new ParticleSystem(10000);
        final Geometry.Vector particleDirection = new Geometry.Vector(0f, 0.5f, 0f);

        globalStartTime = System.nanoTime();

        //fftMatrix[0] = 1.0f;
        //fftMatrix[1] = 0.5f;
        //fftMatrix[2] = 0.25f;
        //fftMatrix[3] = 0.5f;

        table = new Table();

        redSource = new ParticleSource(new Geometry.Point(0f, 0f, 0f),
                particleDirection,
                Color.rgb(255, 50, 5), angleVarDegrees, speedVar);
        greenSource = new ParticleSource(new Geometry.Point(0f, 0f, 0f),
                particleDirection,
                Color.rgb(25, 255, 25), angleVarDegrees, speedVar);
        blueSource = new ParticleSource(new Geometry.Point(0f, 0f, 0f),
                particleDirection,
                Color.rgb(5, 50, 255), angleVarDegrees, speedVar);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height); //set viewport to entire surface

        //create projection matrix with perspective
        MatrixHelper.perspectiveM(
                projectionMatrix,
                45,
                (float) width / (float) height,
                1f,
                10f);
        //define eye view point for view matrix
        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);

    }

    @Override
    public void onDrawFrame(GL10 unused) {
        glClear(GL_COLOR_BUFFER_BIT);

        float currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;

        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        redSource.addParticles(particleSystem, currentTime, 5);
        greenSource.addParticles(particleSystem, currentTime, 5);
        blueSource.addParticles(particleSystem, currentTime, 5);

        particleProgram.useProgram();
        particleProgram.setUniforms(viewProjectionMatrix, currentTime);
        particleSystem.bindData(particleProgram);
        particleSystem.draw();

        //draw table with texture
        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, textureID);
        table.bind(textureProgram);
        table.draw();
    }

    private void positionTableInScene() {
        //table is defined in XY, so rotate by 90 deg to align with XZ
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }


}
