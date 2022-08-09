package com.radiant.randomphysicsengine.renderer;

import java.awt.*;

public class ColoredPolygon extends Polygon {
    public Color color;
    public ColoredPolygon(Polygon p, Color c) {
        super(p.xpoints, p.ypoints, p.npoints);
        color = c;
    }
}
