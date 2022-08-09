package com.radiant.randomphysicsengine.renderer;

import com.radiant.randomphysicsengine.datatypes.coordinates.Vector3;

import java.awt.*;
import java.util.Arrays;

public class RenderPolygon {
    public Vector3[] vertices;
    public Color color;
    public RenderPolygon(Color color, Vector3... vertices) {
        this.vertices = Arrays.stream(vertices).map(Vector3::clone).toArray(Vector3[]::new);
        this.color = color;
    }
    public Polygon toPolygon() {
        Polygon polygon = new Polygon();
        for (Vector3 vertex : vertices) {
            polygon.addPoint((int) vertex.x, (int) vertex.y);
        }
        return polygon;
    }
}