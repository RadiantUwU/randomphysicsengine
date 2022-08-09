package com.radiant.randomphysicsengine.datatypes.coordinates;

import com.radiant.randomphysicsengine.datatypes.render.DepthRenderObject;
import com.radiant.randomphysicsengine.renderer.InverseSquareRoot;

public class Vector3 implements DepthRenderObject, Cloneable {
    public double x;
    public double y;
    public double z;
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public static Vector3 ZERO = new Vector3(0, 0, 0);
    public static Vector3 ONE = new Vector3(1, 1, 1);
    public static Vector3 UP = new Vector3(0, 1, 0);
    public static Vector3 DOWN = new Vector3(0, -1, 0);
    public static Vector3 LEFT = new Vector3(-1, 0, 0);
    public static Vector3 RIGHT = new Vector3(1, 0, 0);
    public static Vector3 FORWARD = new Vector3(0, 0, 1);
    public static Vector3 BACKWARD = new Vector3(0, 0, -1);
    public Vector3 add(double d) {
        return new Vector3(x + d, y + d, z + d);
    }
    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }
    public Vector3 subtract(double d) {
        return new Vector3(x - d, y - d, z - d);
    }
    public Vector3 subtract(Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }
    public Vector3 multiply(double d) {
        return new Vector3(x * d, y * d, z * d);
    }
    public Vector3 multiply(Vector3 v) {
        return new Vector3(x * v.x, y * v.y, z * v.z);
    }
    public Vector3 modulo(double d) {
        return new Vector3(x % d, y % d, z % d);
    }
    public Vector3 modulo(Vector3 v) {
        return new Vector3(x % v.x, y % v.y, z % v.z);
    }
    public Vector3 divide(double d) {
        return new Vector3(x / d, y / d, z / d);
    }
    public Vector3 divide(Vector3 v) {
        return new Vector3(x / v.x, y / v.y, z / v.z);
    }

    public double highest() {
        return Math.max(Math.max(x, y), z);
    }
    public double lowest() {
        return Math.min(Math.min(x, y), z);
    }
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    public Vector3 normalize() {
        double invsqrt = InverseSquareRoot.get((float)(x * x + y * y + z * z));
        return new Vector3(x * invsqrt, y * invsqrt, z * invsqrt);
    }
    public Vector3 rotateDegreesX(double degrees) {
        double radians = Math.toRadians(degrees);
        return new Vector3(x,
                y * Math.cos(radians) - z * Math.sin(radians),
                y * Math.sin(radians) + z * Math.cos(radians));
    }
    public Vector3 rotateDegreesY(double degrees) {
        double radians = Math.toRadians(degrees);
        return new Vector3(x * Math.cos(radians) + z * Math.sin(radians),
                y,
                -x * Math.sin(radians) + z * Math.cos(radians));
    }
    public Vector3 rotateDegreesZ(double degrees) {
        double radians = Math.toRadians(degrees);
        return new Vector3(x * Math.cos(radians) - y * Math.sin(radians),
                x * Math.sin(radians) + y * Math.cos(radians),
                z);
    }
    public Vector3 rotateRadiansX(double radians) {
        return new Vector3(x,
                y * Math.cos(radians) - z * Math.sin(radians),
                y * Math.sin(radians) + z * Math.cos(radians));
    }
    public Vector3 rotateRadiansY(double radians) {
        return new Vector3(x * Math.cos(radians) + z * Math.sin(radians),
                y,
                -x * Math.sin(radians) + z * Math.cos(radians));
    }
    public Vector3 rotateRadiansZ(double radians) {
        return new Vector3(x * Math.cos(radians) - y * Math.sin(radians),
                x * Math.sin(radians) + y * Math.cos(radians),
                z);
    }
    public Vector2 toVector2() {
        return new Vector2(x, y);
    }
    @Override
    public Vector3 clone() {
        try {
            return (Vector3)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public double getDepth() {
        return z;
    }
}
