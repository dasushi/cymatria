package com.app.sucrates.cymatria.objects;

import com.app.sucrates.cymatria.util.Geometry;

import java.util.Random;

import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setRotateEulerM;

/**
 * Created by Stephen on 9/8/2017.
 */
public class ParticleSource {

    private final Geometry.Point position;
    private final Geometry.Vector direction;
    private final int color;

    private final float tanVar;
    private final float speedVar;
    private final float bias = 0.5f;

    private final Random rand = new Random();

    private final float[] rotMatrix = new float[16];
    private final float[] dirVector = new float[4];
    private final float[] resultVector = new float[4];

    public ParticleSource(Geometry.Point pos,
                          Geometry.Vector dir,
                          int col,
                          float angleVarDegrees,
                          float speedVariance){
        position = pos;
        direction = dir;
        color = col;
        angleVar = angleVarDegrees;
        speedVar = speedVariance;

        dirVector[0] = direction.x;
        dirVector[1] = direction.y;
        dirVector[2] = direction.z;

    }

    public void addParticles(ParticleSystem system, float currentTime, int count) {
        for (int i = 0; i < count; i++) {
            setRotateEulerM(rotMatrix,
                    0,
                    (rand.nextFloat() - bias) * tanVar,
                    (rand.nextFloat() - bias) * tanVar,
                    (rand.nextFloat() - bias) * tanVar);
            multiplyMV(resultVector, 0,
                    rotMatrix, 0,
                    dirVector, 0);
            float speedAdj = 1f + rand.nextFloat() * speedVar;
            Geometry.Vector newDirection = new Geometry.Vector(
                    resultVector[0] * speedAdj,
                    resultVector[1] * speedAdj,
                    resultVector[2] * speedAdj
                    );


            system.addParticle(position, color, newDirection, currentTime);
        }
    }
}
