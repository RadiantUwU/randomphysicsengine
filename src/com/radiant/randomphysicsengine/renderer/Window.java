package com.radiant.randomphysicsengine.renderer;

import com.radiant.randomphysicsengine.datatypes.coordinates.UDim2;
import com.radiant.randomphysicsengine.datatypes.coordinates.Vector2;
import com.radiant.randomphysicsengine.datatypes.events.Event;
import com.radiant.randomphysicsengine.datatypes.events.EventCaller;
import com.radiant.randomphysicsengine.datatypes.render.RenderArgs;
import com.radiant.randomphysicsengine.datatypes.render.RenderObject;
import com.radiant.randomphysicsengine.datatypes.render.RenderObject2D;
import com.radiant.randomphysicsengine.datatypes.render.RenderRect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class Window extends Canvas implements KeyListener, MouseMotionListener, Runnable {
    public JFrame frame;
    public double fps = 60.0;
    public int currentFPS = 0;
    private final Renderer3DTask renderer3d;
    public boolean fpsLock = true;
    public boolean wireframeMode = false;
    public String currentTitle = "";
    public Thread thread;
    public ArrayList<Character> pressedKeys = new ArrayList<>();
    public boolean blankMode = true;
    public boolean no3D = false;
    public boolean drawBackground = true;
    public Event onKeyPressed = new Event();
    public Event onKeyReleased = new Event();
    public Event onMouseMoved = new Event();
    final private EventCaller onKeyPressedCaller = new EventCaller(onKeyPressed);
    final private EventCaller onKeyReleasedCaller = new EventCaller(onKeyReleased);
    final private EventCaller onMouseMovedCaller = new EventCaller(onMouseMoved);
    public ArrayList<RenderObject> renderObjects = new ArrayList<>();
    public Camera camera = null;
    Vector2 lastmousepos = null;
    private boolean running;

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        Character c = e.getKeyChar();
        if (pressedKeys.contains(c)) return;
        pressedKeys.add(c);
        System.out.println(e.getKeyChar() + " press");
        onKeyPressedCaller.call(c);
    }
    public void keyReleased(KeyEvent e) {
        Character c = e.getKeyChar();
        try {pressedKeys.remove(c);} catch (Throwable ex) {
            ex.printStackTrace();
        }
        System.out.println(c + " release");
        onKeyReleasedCaller.call(c);
    }
    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        assert camera != null;
        if (lastmousepos == null) {
            lastmousepos = new Vector2(e.getX(), e.getY());
            return;
        }
        Vector2 mousepos = new Vector2(e.getX(), e.getY());
        Vector2 delta = mousepos.subtract(lastmousepos).multiply(-1);
        camera.rotate(delta);
        onMouseMovedCaller.call(mousepos,delta);
    }
    static protected Window instance = null;
    static public Window getInstance() {
        if (instance == null) {
            instance = new Window();
        }
        return instance;
    }
    public void setTitle(String title) {
        frame.setTitle(title);
        currentTitle = title;
    }
    public void setSize(int width, int height) {
        frame.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));
    }
    public Vector2 getMousePos() {
        return new Vector2(MouseInfo.getPointerInfo().getLocation());
    }
    public int getWidth() {
        return frame.getWidth();
    }
    public int getHeight() {
        return frame.getHeight();
    }
    protected Window() {
        frame = new JFrame();
        frame.addKeyListener(this);
        frame.addMouseMotionListener(this);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        this.setSize(800,600);
        this.frame.setResizable(false);
        renderer3d = new Renderer3DTask(this);
    }
    public synchronized void addRenderObject(RenderObject ro) {
        renderObjects.add(ro);
    }
    public synchronized void removeRenderObject(RenderObject ro) {
        renderObjects.remove(ro);
    }
    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "Display");
        this.thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0;
        double delta = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            double FPSConst = ns/fps;
            delta += (now - lastTime) / FPSConst;
            lastTime = now;
            while (delta >= 1) {
                render();
                frames++;
                update();
                delta--;
            }
            Thread.yield();
            if (fpsLock) {
                int sleepTime = (int)(((long)(FPSConst / 1000000) - (long)(delta * FPSConst / 1000000)) );
                if (sleepTime > 0)
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                currentFPS = frames;
                frame.setTitle(currentTitle + " | FPS: " + currentFPS);
                frames = 0;
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        if (g instanceof Graphics2D)
            ((Graphics2D)g).setStroke(new BasicStroke(2));
        if (drawBackground) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (!blankMode) {
            Stream<RenderObject2D> ros2d = renderObjects.stream().filter(Objects::nonNull).filter(RenderObject::visible).map(ro -> { //intermediary
                if (ro instanceof RenderObject2D) return (RenderObject2D)ro;
                return null;
            }).filter(Objects::nonNull);
            Stream<RenderObject> ros3d = renderObjects.stream().filter(Objects::nonNull).filter(RenderObject::visible).filter(ro -> !(ro instanceof RenderObject2D)); //3D
            ArrayList<RenderRect> rosRect = new ArrayList<>(); //2D
            ArrayList<RenderPolygon> allRenderPolygons = new ArrayList<>(); //2D
            ros2d.forEachOrdered(ro -> {
                rosRect.addAll(ro.getRects());
                allRenderPolygons.addAll(ro.getTriangles());
            });
            ros2d.close();
            RenderArgs args = new RenderArgs(g, Vector2.ZERO, wireframeMode, new UDim2(getWidth(), getHeight()), 1);
            for (RenderRect rect : rosRect) {
                rect.render(args);
            }
            for (RenderPolygon t : allRenderPolygons) {
                g.setColor(t.color);
                if (wireframeMode)
                    g.drawPolygon(t.toPolygon());
                else
                    g.fillPolygon(t.toPolygon());
            }
            rosRect.clear();
            allRenderPolygons.clear();
            if (!no3D) {
                assert camera != null;
                renderer3d.reset();
                ros3d.forEachOrdered(renderer3d);
                renderer3d.load();
                ColoredPolygon poly = renderer3d.get();
                while (poly != null) {
                    g.setColor(poly.color);
                    if (wireframeMode)
                        g.drawPolygon(poly);
                    else
                        g.fillPolygon(poly);
                    poly = renderer3d.get();
                }
            }
        }
        g.dispose();
        bs.show();
    }

    private void update() {

    }
}
