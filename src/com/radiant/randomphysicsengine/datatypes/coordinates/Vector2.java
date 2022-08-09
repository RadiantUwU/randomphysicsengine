package com.radiant.randomphysicsengine.datatypes.coordinates;

import com.radiant.randomphysicsengine.renderer.InverseSquareRoot;

import java.awt.*;

public class Vector2 implements Cloneable {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }
    public Vector2(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
    public static Vector2 ZERO = new Vector2(0, 0);
    public static Vector2 ONE = new Vector2(1, 1);
    public static Vector2 UP = new Vector2(0, 1);
    public static Vector2 DOWN = new Vector2(0, -1);
    public static Vector2 LEFT = new Vector2(-1, 0);
    public static Vector2 RIGHT = new Vector2(1, 0);

    public Vector2 add(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }
    public Vector2 subtract(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }
    public Vector2 multiply(double d) {
        return new Vector2(x * d, y * d);
    }
    public Vector2 multiply(Vector2 v) {
        return new Vector2(x * v.x, y * v.y);
    }
    public Vector2 divide(double d) {
        return new Vector2(x / d, y / d);
    }
    public Vector2 divide(Vector2 v) {
        return new Vector2(x / v.x, y / v.y);
    }
    public Vector2 modulo(double d) {
        return new Vector2(x % d, y % d);
    }
    public Vector2 modulo(Vector2 v) {
        return new Vector2(x % v.x, y % v.y);
    }

    public double highest() {
        return Math.max(x, y);
    }
    public double lowest() {
        return Math.min(x, y);
    }
    public double length() {
        return Math.sqrt(x * x + y * y);
    }
    public Vector2 normalize() {
        double invsqrt = InverseSquareRoot.get((float)(x * x + y * y));
        return new Vector2(x * invsqrt, y * invsqrt);
    }

    public Vector2 rotateDegreesX(double degrees) {
        double radians = Math.toRadians(degrees);
        return new Vector2(x,
                y * Math.cos(radians) - 0 * Math.sin(radians));
    }
    public Vector2 rotateDegreesY(double degrees) {
        double radians = Math.toRadians(degrees);
        return new Vector2(x * Math.cos(radians) + 0 * Math.sin(radians),
                y);
    }
    public Vector2 rotateRadiansX(double radians) {
        return new Vector2(x,
                y * Math.cos(radians) - 0 * Math.sin(radians));
    }
    public Vector2 rotateRadiansY(double radians) {
        return new Vector2(x * Math.cos(radians) + 0 * Math.sin(radians),
                y);
    }
    @Override
    public Vector2 clone() {
        try {
            return (Vector2)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public IntVector2 integerify() {
        return new IntVector2((long)x, (long)y);
    }
    public Point toPoint() {
        return new Point((int)x, (int)y);
    }

}
