package com.radiant.randomphysicsengine;

import com.radiant.randomphysicsengine.datatypes.coordinates.Vector3;
import com.radiant.randomphysicsengine.renderer.Window;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    final private static Object lock = new Object();
    private static Window window = null;
    public static void main(String[] args) {
        window = Window.getInstance();
        window.setTitle("uwu");
        window.setSize(800, 600);
        window.renderObjects.add(new TestObject());
        window.camera = new Player();
        window.camera.setPosition(new Vector3(0, 0, 0));
        window.start();
        ((Player)window.camera).safe_to_start_walkThread();
        window.blankMode = false;
        waitForClose();
        window.stop();
        System.exit(0);
    }
    private static void waitForClose() {
        Thread t = new Thread(() -> {
            synchronized(lock) {
                while (window.frame.isVisible())
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
        t.start();
//
        window.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                synchronized (lock) {
                    Window.getInstance().frame.setVisible(false);
                    lock.notify();
                }
            }
//
        });
//
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}