package com.radiant.randomphysicsengine.datatypes.coordinates;

import com.radiant.randomphysicsengine.renderer.InverseSquareRoot;

import java.awt.*;

public class IntVector2 implements Cloneable {
    public long x;
    public long y;

    public IntVector2(long x, long y) {
        this.x = x;
        this.y = y;
    }
    public IntVector2(double x, double y) {
        this.x = (long)x;
        this.y = (long)y;
    }
    public IntVector2(IntVector2 v) {
        this.x = v.x;
        this.y = v.y;
    }
    public IntVector2() {
        this.x = 0;
        this.y = 0;
    }
    public IntVector2(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
    public static IntVector2 ZERO = new IntVector2(0, 0);
    public static IntVector2 ONE = new IntVector2(1, 1);
    public static IntVector2 UP = new IntVector2(0, 1);
    public static IntVector2 DOWN = new IntVector2(0, -1);
    public static IntVector2 LEFT = new IntVector2(-1, 0);
    public static IntVector2 RIGHT = new IntVector2(1, 0);

    public IntVector2 add(IntVector2 v) {
        return new IntVector2(x + v.x, y + v.y);
    }
    public IntVector2 subtract(IntVector2 v) {
        return new IntVector2(x - v.x, y - v.y);
    }
    public IntVector2 multiply(double d) {
        return new IntVector2(x * d, y * d);
    }
    public IntVector2 multiply(IntVector2 v) {
        return new IntVector2(x * v.x, y * v.y);
    }
    public IntVector2 divide(double d) {
        return new IntVector2(x / d, y / d);
    }
    public IntVector2 divide(IntVector2 v) {
        return new IntVector2(x / v.x, y / v.y);
    }
    public IntVector2 modulo(double d) {
        return new IntVector2(x % d, y % d);
    }
    public IntVector2 modulo(IntVector2 v) {
        return new IntVector2(x % v.x, y % v.y);
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
    public IntVector2 normalize() {
        double invsqrt = InverseSquareRoot.get((float)(x * x + y * y));
        return new IntVector2(x * invsqrt, y * invsqrt);
    }

    public IntVector2 rotateDegreesX(double degrees) {
        double radians = Math.toRadians(degrees);
        return new IntVector2(x,
                y * Math.cos(radians) - 0 * Math.sin(radians));
    }
    public IntVector2 rotateDegreesY(double degrees) {
        double radians = Math.toRadians(degrees);
        return new IntVector2(x * Math.cos(radians) + 0 * Math.sin(radians),
                y);
    }
    public IntVector2 rotateRadiansX(double radians) {
        return new IntVector2(x,
                y * Math.cos(radians) - 0 * Math.sin(radians));
    }
    public IntVector2 rotateRadiansY(double radians) {
        return new IntVector2(x * Math.cos(radians) + 0 * Math.sin(radians),
                y);
    }
    @Override
    public IntVector2 clone() {
        try {
            return (IntVector2)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public Point toPoint() {
        return new Point((int)x, (int)y);
    }
}
