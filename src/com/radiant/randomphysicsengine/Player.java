package com.radiant.randomphysicsengine;

import com.radiant.randomphysicsengine.datatypes.coordinates.Vector2;
import com.radiant.randomphysicsengine.datatypes.coordinates.Vector3;
import com.radiant.randomphysicsengine.renderer.Camera;
import com.radiant.randomphysicsengine.renderer.Window;

import java.util.ArrayList;

public class Player implements Camera {
    public Player() {
        walkThread = new Thread(() -> {
            Player player = Player.this;
            while (true) {
                try {
                    Thread.sleep(stepTime_walkThread);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long realStepTime = System.currentTimeMillis() - player.lastTime_walkThread;
                player.lastTime_walkThread = System.currentTimeMillis();
                double s = realStepTime / 1000.0;
                ArrayList<Character> pressedKeys = Window.getInstance().pressedKeys;
                if (pressedKeys.contains('w')) {
                    player.move(Camera.MovementDirection.FORWARD, s);
                }
                if (pressedKeys.contains('s')) {
                    player.move(Camera.MovementDirection.BACKWARD, s);
                }
                if (pressedKeys.contains('a')) {
                    player.move(Camera.MovementDirection.LEFT, s);
                }
                if (pressedKeys.contains('d')) {
                    player.move(Camera.MovementDirection.RIGHT, s);
                }
                if (pressedKeys.contains('q')) {
                    player.move(Camera.MovementDirection.UP, s);
                }
                if (pressedKeys.contains('e')) {
                    player.move(Camera.MovementDirection.DOWN, s);
                }
            }
        });
    }
    private final Thread walkThread;
    private long lastTime_walkThread;
    final static private long stepTime_walkThread = 10;
    public void safe_to_start_walkThread() {
        if (lastTime_walkThread == 0) {
            lastTime_walkThread = System.currentTimeMillis();
            walkThread.start();
        }
    }
    protected Vector3 currentpos = new Vector3(0, 0, 0);
    protected Vector2 currentrot = new Vector2(0, 0);
    public float mousesensitivity = 0.7f;
    public float speed = 4f;
    public Vector3 getPosition() {
        return currentpos;
    }
    public Vector2 getRotation() {
        return currentrot;
    }
    public void setPosition(Vector3 pos) {
        currentpos=pos;
    }
    public void setRotation(Vector2 rot) {
        currentrot=rot;
    }
    public void _rawMove(Vector3 move) {
        currentpos = currentpos.add(move);
    }
    public void move(Vector3 move) {
        Vector3 mv = new Vector3(0,0,0);
        mv.x -= (float) (Math.sin(Math.toRadians(currentrot.x)) * speed * move.z);
        mv.z += (float) (Math.cos(Math.toRadians(currentrot.x)) * speed * move.z);
        mv.y = (float) (speed * move.y);
        _rawMove(mv);
    }
    public void moveSides(Vector3 move)  {
        Vector3 mv = move.multiply(speed);
        mv.x += (float) (Math.sin(Math.toRadians(currentrot.x)) * speed);
        mv.z += (float) (Math.cos(Math.toRadians(currentrot.x)) * speed);
        _rawMove(mv);
    }
    public void move(MovementDirection direction, double amount) {
        switch (direction) {
            case FORWARD -> move(Vector3.FORWARD.multiply(amount));
            case BACKWARD -> move(Vector3.BACKWARD.multiply(amount));
            case LEFT -> moveSides(Vector3.LEFT.multiply(amount));
            case RIGHT -> moveSides(Vector3.RIGHT.multiply(amount));
            case UP -> _rawMove(Vector3.UP.multiply(speed * amount));
            case DOWN -> _rawMove(Vector3.DOWN.multiply(speed * amount));
            default -> {}
        }
    }
    public void rotate(Vector2 rot) {
        currentrot = currentrot
                .add(rot.multiply(mousesensitivity))
                .add(new Vector2(180,0))
                .modulo(new Vector2(360,999999))
                .subtract(new Vector2(180,0));
        currentrot.y=Math.max(Math.min(currentrot.y,90),-90);
    }
}
