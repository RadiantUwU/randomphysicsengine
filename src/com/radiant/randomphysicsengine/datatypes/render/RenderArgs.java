package com.radiant.randomphysicsengine.datatypes.render;

import com.radiant.randomphysicsengine.datatypes.coordinates.UDim2;
import com.radiant.randomphysicsengine.datatypes.coordinates.Vector2;

import java.awt.*;

public record RenderArgs(Graphics g, Vector2 relativeOffset, boolean wireframeMode, UDim2 relativeScale, double relativetransparency) {
    public RenderArgs(Graphics g, Vector2 relativeOffset, boolean wireframeMode) {
        this(g, relativeOffset, wireframeMode, UDim2.ONE, 1);
    }
    public RenderArgs(Graphics g, Vector2 relativeOffset) {
        this(g, relativeOffset, false, UDim2.ONE, 1);
    }
    public RenderArgs changeRelativeScale(UDim2 relativeScale) {
        return new RenderArgs(g, relativeOffset, wireframeMode, relativeScale.multiply(this.relativeScale), relativetransparency);
    }
    public RenderArgs changeRelativeTransparency(double relativetransparency) {
        return new RenderArgs(g, relativeOffset, wireframeMode, relativeScale, relativetransparency * this.relativetransparency);
    }
    public RenderArgs changeRelativeOffset(Vector2 relativeOffset) {
        return new RenderArgs(g, relativeOffset.add(this.relativeOffset), wireframeMode, relativeScale, relativetransparency);
    }
}
