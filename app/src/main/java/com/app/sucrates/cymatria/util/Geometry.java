package com.app.sucrates.cymatria.util;

/**
 * Created by Stephen on 7/18/2017.
 */
public class Geometry {

    public static class Point {
        public final float x, y, z;
        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point translateY(float dist) {
            return new Point(x, y + dist, z);
        }

        public Point translate(Vector vec) {
            return new Point(x + vec.x,
                    y + vec.y,
                    z + vec.z);
        }
    }

    public static class Circle {
        public final Point center;
        public final float radius;

        public Circle(Point cent, float rad){
            center = cent;
            radius = rad;
        }

        public Circle scale(float scaleFactor) {
            return new Circle(center, radius * scaleFactor);
        }
    }

    public static class Cylinder {
        public final Point center;
        public final float radius;
        public final float height;

        public Cylinder(Point cent, float rad, float h) {
            center = cent;
            radius = rad;
            height = h;
        }
    }

    public static class Vector {
        public final float x, y, z;

        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public static Vector vectorBetween(Point nearPoint, Point farPoint) {
            return new Vector(farPoint.x - nearPoint.x,
                    farPoint.y - nearPoint.y,
                    farPoint.z - nearPoint.z);
        }

        public float length() {
            return (float) Math.sqrt(x * x + y * y + z * z);
        }

        public float dotProduct(Vector otherVec) {
            return x * otherVec.x + y * otherVec.y + z * otherVec.z;
        }

        public Vector crossProduct(Vector otherVec) {
            return new Vector(
                    (y * otherVec.z) - (z * otherVec.y),
                    (z * otherVec.x) - (x * otherVec.z),
                    (x * otherVec.y) - (y * otherVec.x));
        }

        public Vector scale(float factor) {
            return new Vector(
                    x * factor,
                    y * factor,
                    z * factor);
        }

    }

    public static class Ray {

        public final Point point;
        public final Vector vector;

        public Ray(Point pnt, Vector vect) {
            point = pnt;
            vector = vect;
        }

    }
    public static class Sphere {

        public final Point center;
        public final float radius;
        public Sphere(Point cent, float rad) {
            center = cent;
            radius = rad;
        }

    }

    public static class Plane {
        public final Point point;
        public final Vector normal;

        public Plane(Point pnt, Vector norm) {
            point = pnt;
            normal = norm;
        }
    }

    public static boolean intersects(Sphere sphere, Ray ray) {
        return distanceBetween(sphere.center, ray) < sphere.radius;
    }

    public static Point intersectionPoint(Ray ray, Plane plane) {
        Vector rayPlaneVector = Vector.vectorBetween(ray.point, plane.point);
        float scaleFactor = rayPlaneVector.dotProduct(plane.normal)
                / ray.vector.dotProduct(plane.normal);
        Point intersectPoint = ray.point.translate(ray.vector.scale(scaleFactor));
        return intersectPoint;
    }

    public static float distanceBetween(Point point, Ray ray) {
        Vector p1Point = Vector.vectorBetween(ray.point, point);
        Vector p2Point = Vector.vectorBetween(ray.point.translate(ray.vector), point);

        float areaOfTriangleDoubled = p1Point.crossProduct(p2Point).length();
        float baseLength = ray.vector.length();

        float distPointToRay = areaOfTriangleDoubled / baseLength;
        return distPointToRay;
    }



}
