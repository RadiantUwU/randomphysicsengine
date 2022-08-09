package com.radiant.randomphysicsengine.datatypes.render;

import com.radiant.randomphysicsengine.renderer.RenderPolygon;

import java.util.ArrayList;

public interface RenderObject {
    ArrayList<RenderPolygon> getTriangles();
    default boolean visible() {
        return true;
    }
}
