package com.radiant.randomphysicsengine.datatypes.coordinates;

public class UDim2 {
    public UDim x;
    public UDim y;
    public UDim2(UDim x, UDim y) {
        this.x = x;
        this.y = y;
    }
    public UDim2(double ax, double rx, double ay, double ry) {
        this.x = new UDim(ax, rx);
        this.y = new UDim(ay, ry);
    }
    public UDim2(double ax, double ay) {
        this.x = new UDim(ax, 1);
        this.y = new UDim(ay, 1);
    }
    public UDim2 add(UDim2 o) {
        return new UDim2(this.x.add(o.x), this.y.add(o.y));
    }
    public UDim2 subtract(UDim2 o) {
        return new UDim2(this.x.subtract(o.x), this.y.subtract(o.y));
    }
    public UDim2 multiply(UDim2 o) {
        return new UDim2(this.x.multiply(o.x), this.y.multiply(o.y));
    }
    public static UDim2 ZERO = new UDim2(UDim.ZERO, UDim.ZERO);
    public static UDim2 ONE = new UDim2(UDim.ONE, UDim.ONE);
}
