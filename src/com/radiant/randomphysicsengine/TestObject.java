package com.radiant.randomphysicsengine;

import com.radiant.randomphysicsengine.datatypes.render.RenderObject;
import com.radiant.randomphysicsengine.datatypes.coordinates.Vector3;
import com.radiant.randomphysicsengine.renderer.RenderPolygon;

import java.awt.*;
import java.util.ArrayList;

public class TestObject implements RenderObject {
    public ArrayList<RenderPolygon> getTriangles() {
        ArrayList<RenderPolygon> renderPolygons = new ArrayList<>();
        renderPolygons.add(new RenderPolygon(
                Color.RED,
                new Vector3(1, 0, 0),
                new Vector3(0.5, -0.5, 0),
                new Vector3(0, 0, 0),
                new Vector3(0, 1, 0)
        ));
        return renderPolygons;
    }
    public boolean visible() {
        return true;
    }
}
