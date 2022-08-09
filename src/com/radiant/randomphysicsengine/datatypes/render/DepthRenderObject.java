package com.radiant.randomphysicsengine.datatypes.render;

public interface DepthRenderObject extends Comparable<DepthRenderObject> {
    double getDepth();
    @Override
    default int compareTo(DepthRenderObject o) {
        return Double.compare(getDepth(), o.getDepth());
    }
}
