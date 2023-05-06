package game.engine;

import game.BomberMan;
import game.entity.models.Entity;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Observer;

public class GarbageCollectorTask {
    private static final int DELAY_MS = 60 * 1000;
    private final ActionListener taskPerformer = evt -> {
        System.gc();
    };

    private final Timer timer = new Timer(DELAY_MS, taskPerformer);

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
