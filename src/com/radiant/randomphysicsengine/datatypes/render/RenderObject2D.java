package com.radiant.randomphysicsengine.datatypes.render;

import com.radiant.randomphysicsengine.renderer.RenderPolygon;

import java.util.ArrayList;

public interface RenderObject2D extends RenderObject {
    ArrayList<RenderPolygon> getTriangles();
    ArrayList<RenderRect> getRects();
    default boolean visible() {
        return true;
    }
}
