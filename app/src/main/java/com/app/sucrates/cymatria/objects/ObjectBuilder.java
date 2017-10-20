package com.app.sucrates.cymatria.objects;

import com.app.sucrates.cymatria.util.Geometry;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;

public class ObjectBuilder {

    static interface DrawCommand{
        void draw();
    }

    //static holder class for vertexData and drawCommands
    static class GeneratedData {
        final float[] vertexData;
        final List<DrawCommand> drawCommandList;

        GeneratedData(float[] vertData, List<DrawCommand> drawList) {
            vertexData = vertData;
            drawCommandList = drawList;
        }
    }

    //builds data into GeneratedData bundle
    private GeneratedData build() {
        return new GeneratedData(vertexData, drawCommandList);
    }

    private static final int FLOATS_PER_VERTEX = 3;
    private final float[] vertexData;
    private final List<DrawCommand> drawCommandList = new ArrayList<DrawCommand>();

    private int offset = 0;

    private ObjectBuilder(int sizeInVertices) {
        vertexData = new float[sizeInVertices * FLOATS_PER_VERTEX];
    }

    private static int sizeOfCircleInVertices(int numPoints) {
        return 1 + (numPoints + 1);
    }

    private static int sizeOfOpenCylinderInVertices(int numPoints) {
        return (numPoints + 1) * 2;
    }

    private void appendCircle(Geometry.Circle circle, int numPoints) {
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfCircleInVertices(numPoints);
        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;

        for(int i = 0; i <= numPoints; i++) {
            float angleInRad = ((float) i / (float) numPoints) * ((float) Math.PI * 2f);
            vertexData[offset++] = circle.center.x + circle.radius * (float) Math.cos(angleInRad);
            vertexData[offset++] = circle.center.y;
            vertexData[offset++] = circle.center.z + circle.radius * (float) Math.sin(angleInRad);
        }
        drawCommandList.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_FAN, startVertex, numVertices);
            }
        });
    }

    private void appendOpenCylinder(Geometry.Cylinder cylinder, int numPoints) {
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfOpenCylinderInVertices(numPoints);
        final float yStart = cylinder.center.y - (cylinder.height / 2f);
        final float yEnd = cylinder.center.y + (cylinder.height / 2f);

        for(int i = 0; i <= numPoints; i++) {
            float angleRads = ((float) i / (float) numPoints) * ((float) Math.PI * 2f);
            float xPos = cylinder.center.x + cylinder.radius * (float)Math.cos(angleRads);
            float zPos = cylinder.center.z + cylinder.radius * (float)Math.sin(angleRads);

            vertexData[offset++] = xPos;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPos;

            vertexData[offset++] = xPos;
            vertexData[offset++] = yEnd;
            vertexData[offset++] = zPos;
        }

        drawCommandList.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, numVertices);
            }
        });

    }

}
