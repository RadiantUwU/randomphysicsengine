package com.radiant.randomphysicsengine.renderer;

import com.radiant.randomphysicsengine.datatypes.render.RenderObject;
import com.radiant.randomphysicsengine.datatypes.coordinates.Vector2;
import com.radiant.randomphysicsengine.datatypes.coordinates.Vector3;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

final public class Renderer3DTask implements Supplier<ColoredPolygon>, Consumer<RenderObject> {
    private boolean loading = true;
    private final ArrayList<RenderPolygon> tris;
    private final Window window;
    private Vector2 center;
    private double scale;
    private Vector3 cameraPos;
    private Vector2 cameraRot;
    private Point task2d(Vector3 pos) {
        return scale(pos.multiply(10).subtract(cameraPos)).add(center).toPoint();
    }

    private static Vector2 scale(Vector3 pos) {
        double dist = pos.toVector2().length();
        double theta = Math.atan2(pos.y, pos.x);
        double depth2 = 15 - pos.z;
        double localScale = Math.abs(1400/(depth2+1400));
        return new Vector2(
                (int) (dist * localScale * Math.cos(theta)),
                (int) (dist * localScale * Math.sin(theta))
        );
    }
    private ColoredPolygon task(RenderPolygon t) {
        int arrsize = t.vertices.length;
        Point[] points = new Point[arrsize];
        for (int i = 0; i < arrsize; i++) {
            points[i] = task2d(t.vertices[i]);
        }
        Polygon poly = new Polygon();
        for (int i = 0; i < arrsize; i++) {
            poly.addPoint(points[i].x, points[i].y);
        }
        return new ColoredPolygon(poly, t.color);
    }
    @Override
    public ColoredPolygon get() throws NullPointerException {
        if (loading) throw new NullPointerException("Renderer3DTask is still getting data.");
        if (!tris.isEmpty()) {
            return task(tris.remove(0));
        }
        return null;
    }
    @Override
    public void accept(RenderObject t) throws NullPointerException {
        if (loading) {
            tris.addAll(t.getTriangles());
        } else throw new NullPointerException("Renderer3DTask has finished loading data.");
    }
    public void load() {
        loading = false;
        Camera camera = window.camera;
        center = new Vector2((double)window.getWidth() / 2, (double)window.getHeight() / 2); //fast lookup
        cameraPos = camera.getPosition();
        cameraRot = camera.getRotation();
        scale = camera.getScale();
    }
    public Renderer3DTask(Window window) {
        tris = new ArrayList<>();
        this.window = window;
    }
    public void reset() {
        tris.clear();
        loading = true;
    }
}
