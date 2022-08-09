package com.radiant.randomphysicsengine.datatypes.coordinates;

public class UDim {
    public double absolute;
    public double relative;
    public UDim(double absolute, double relative) {
        this.absolute = absolute;
        this.relative = relative;
    }
    public UDim(double absolute) {
        this.absolute = absolute;
        this.relative = 1;
    }
    public UDim add(UDim other) {
        return new UDim(this.absolute + other.absolute, this.relative + other.relative);
    }
    public UDim subtract(UDim other) {
        return new UDim(this.absolute - other.absolute, this.relative - other.relative);
    }
    public UDim multiply(UDim other) {
        return new UDim((this.absolute + other.absolute) * other.relative * this.relative, 1);
    }
    public static UDim ZERO = new UDim(0, 0);
    public static UDim ONE = new UDim(0, 1);
}
