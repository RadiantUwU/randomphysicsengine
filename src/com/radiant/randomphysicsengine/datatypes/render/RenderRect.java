package com.radiant.randomphysicsengine.datatypes.render;

import com.radiant.randomphysicsengine.datatypes.InheritableObject;
import com.radiant.randomphysicsengine.datatypes.coordinates.Rect;
import com.radiant.randomphysicsengine.datatypes.coordinates.UDim2;
import com.radiant.randomphysicsengine.datatypes.coordinates.Vector2;
import com.radiant.randomphysicsengine.datatypes.coordinates.Vector3;
import com.radiant.randomphysicsengine.datatypes.exceptions.RecursionError;
import com.radiant.randomphysicsengine.renderer.RenderPolygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RenderRect extends Rect implements InheritableObject, RenderObject2D{
    private InheritableObject parent = null;
    private boolean isvisible = true;
    private boolean renderLock = false;
    public UDim2 size = UDim2.ZERO;
    public Color color = Color.BLACK;
    private Set<InheritableObject> children;
    public InheritableObject getParent() {
        return parent;
    }
    public void setParent(InheritableObject parent) {
        if (this.parent != null)
            this.parent.removeChild(this);
        this.parent = parent;
        if (this.parent != null)
            this.parent.addChild(this);
    }
    public void addChild(InheritableObject child) {
        children.add(child);
    }
    public void removeChild(InheritableObject child) {
        children.remove(child);
    }
    public Set<InheritableObject> getChildren() {
        return children;
    }
    public RenderRect(Vector2 position, Vector2 size) {
        super(position, size);
    }
    public RenderRect(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    public RenderRect() {
        super();
    }
    public ArrayList<RenderPolygon> getTriangles() {
        return new ArrayList<>();
    }
    public ArrayList<RenderRect> getRects() {
        ArrayList<RenderRect> rects = new ArrayList<>();
        rects.add(this);
        return rects;
    }
    private void _render(RenderArgs args) {
        Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * args.relativetransparency()));
        args.g().setColor(c);
        Vector2 v = args.relativeOffset().add(position);
        UDim2 u = this.size.multiply(args.relativeScale());
        if (args.wireframeMode()) {
            args.g().drawRect((int)v.x, (int)v.y, (int)u.x.absolute, (int)u.y.absolute);
        } else {
            args.g().fillRect((int)v.x, (int)v.y, (int)u.x.absolute, (int)u.y.absolute);
        }
    }
    public void render(RenderArgs args) {
        this._render(args);
        RenderArgs newArgs = args.changeRelativeOffset(position).changeRelativeScale(size).changeRelativeTransparency(color.getAlpha());
        for (RenderRect child : _getRects()) {
            child.render(newArgs);
        }
    }
    private Set<RenderRect> _getRects() {
        Set<RenderRect> s = new HashSet<>();
        this.getChildren().stream().filter(o -> o instanceof RenderRect).forEach(o -> s.add((RenderRect)o));
        return s;
    }
    public ArrayList<RenderRect> allRects() {
        if (!isvisible) return new ArrayList<>();
        if (renderLock) {
            throw new RecursionError("RecursionError: RenderRect.allRects()");
        }
        renderLock = true;
        ArrayList<RenderRect> rects = new ArrayList<>();
        rects.add(this);
        this.getChildren().stream().filter(child -> child instanceof RenderRect).forEachOrdered(child -> rects.addAll(((RenderRect) child).allRects()));
        renderLock = false;
        return rects;
    }

    public boolean visible() {
        return true;
    }
    public void setVisible(boolean visible) {
        isvisible = visible;
    }
}
