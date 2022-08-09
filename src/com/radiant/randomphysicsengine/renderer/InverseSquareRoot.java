package com.radiant.randomphysicsengine.renderer;

final public class InverseSquareRoot {
    private static native float _get(float x);
    private static float _get_java(float x) {
        final float threehalves = 1.5F;
        float x2 = x * 0.5F;
        float y;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        y = Float.intBitsToFloat(i);
        y = y * (threehalves - (x2 * y * y));
        return y;
    }
    static private boolean initFail = false;
    public static float get(float x) {
        if (!initFail) return _get(x);
        else return _get_java(x);
    }
    static {
        System.out.println("Initializing library located at " + System.getProperty("user.dir") + "/lib/InverseSquareRoot.so");
        try {
            System.load(System.getProperty("user.dir") + "/lib/InverseSquareRoot.so");
            System.out.println("Initialized successfully.");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Failed to initialize library \"InverseSquareRoot.so\".");
            initFail = true;
        }
    }
}
