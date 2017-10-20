package com.app.sucrates.cymatria.objects;

import android.graphics.Color;

import com.app.sucrates.cymatria.data.VertexArray;
import com.app.sucrates.cymatria.ogl.ParticleShaderProgram;
import com.app.sucrates.cymatria.util.Constants;
import com.app.sucrates.cymatria.util.Geometry;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by Stephen on 9/8/2017.
 */
public class ParticleSystem {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int VECTOR_COMPONENT_COUNT = 3;
    private static final int PARTICLE_START_TIME_COMPONENT_COUNT  = 1;

    private static final int TOTAL_COMPONENT_COUNT =
                                POSITION_COMPONENT_COUNT +
                                COLOR_COMPONENT_COUNT +
                                VECTOR_COMPONENT_COUNT +
                                PARTICLE_START_TIME_COMPONENT_COUNT;

    private static final int STRIDE = TOTAL_COMPONENT_COUNT * Constants.BYTES_PER_FLOAT;

    private final float[] particles;
    private final VertexArray vertexArray;
    private final int maxParticleCount;

    private int currentParticleCount;
    private int nextParticle;

    public ParticleSystem(int maxParticleCount) {
        particles = new float[maxParticleCount * TOTAL_COMPONENT_COUNT];
        vertexArray = new VertexArray(particles);
        this.maxParticleCount = maxParticleCount;
    }

    public void addParticle(Geometry.Point position,
                            int color,
                            Geometry.Vector direction,
                            float particleStartTime) {
        final int particleOffset = nextParticle * TOTAL_COMPONENT_COUNT;

        int currentOffset = particleOffset;
        nextParticle++;

        if(currentParticleCount < maxParticleCount) {
            currentParticleCount++;
        }

        if(nextParticle == maxParticleCount) {
            //start over while keeping currentParticleCount so other particles are still drawn
            nextParticle = 0;
        }

        //set particle positions
        particles[currentOffset++] = position.x;
        particles[currentOffset++] = position.y;
        particles[currentOffset++] = position.z;

        //set particle colors
        particles[currentOffset++] = Color.red(color) / 255f;
        particles[currentOffset++] = Color.green(color) / 255f;
        particles[currentOffset++] = Color.blue(color) / 255f;

        //set particle directions
        particles[currentOffset++] = direction.x;
        particles[currentOffset++] = direction.y;
        particles[currentOffset++] = direction.z;

        //set start time
        particles[currentOffset++] = particleStartTime;

        //update vertex array with data
        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);

    }

    public void bindData(ParticleShaderProgram particleProgram) {
        int dataOffset = 0;
        vertexArray.setVertexAttributePointer(
                dataOffset,
                particleProgram.getPositionAttribLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
        dataOffset += POSITION_COMPONENT_COUNT;

        vertexArray.setVertexAttributePointer(
                dataOffset,
                particleProgram.getColorAttribLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;

        vertexArray.setVertexAttributePointer(
                dataOffset,
                particleProgram.getDirectionVectorAttribLocation(),
                VECTOR_COMPONENT_COUNT,
                STRIDE);
        dataOffset += VECTOR_COMPONENT_COUNT;

        vertexArray.setVertexAttributePointer(
                dataOffset,
                particleProgram.getParticleStartTimeAttribLocation(),
                PARTICLE_START_TIME_COMPONENT_COUNT,
                STRIDE);

    }

    public void draw() {
        glDrawArrays(GL_POINTS, 0, currentParticleCount);
    }


}
