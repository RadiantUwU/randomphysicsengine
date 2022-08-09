package com.radiant.randomphysicsengine.datatypes.coordinates;

import com.radiant.randomphysicsengine.datatypes.render.DepthRenderObject;

public class Rect {
    public Vector2 position;
    public Vector2 size;
    public Rect(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }
    public Rect(double x, double y, double width, double height) {
        this.position = new Vector2(x, y);
        this.size = new Vector2(width, height);
    }
    public Rect() {
        this.position = new Vector2(0, 0);
        this.size = new Vector2(0, 0);
    }
}
