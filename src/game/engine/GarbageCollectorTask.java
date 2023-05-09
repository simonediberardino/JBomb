package game.engine;

import javax.swing.*;
import java.awt.event.ActionListener;

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
