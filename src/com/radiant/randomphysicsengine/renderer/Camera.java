package com.radiant.randomphysicsengine.renderer;

import com.radiant.randomphysicsengine.datatypes.coordinates.Vector2;
import com.radiant.randomphysicsengine.datatypes.coordinates.Vector3;

public interface Camera {
    enum MovementDirection {
        UNKNOWN, FORWARD, BACKWARD, LEFT, RIGHT, UP, DOWN
    }
    Vector3 getPosition();
    Vector2 getRotation();
    void setPosition(Vector3 pos);
    void setRotation(Vector2 rot);
    void _rawMove(Vector3 move);
    void move(Vector3 move);
    void move(MovementDirection direction, double amount);
    void rotate(Vector2 rot);
    default double getScale() {
        return 1;
    }
}
