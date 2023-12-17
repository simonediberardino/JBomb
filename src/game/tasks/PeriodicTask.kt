package game.tasks;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class PeriodicTask {
    private final Runnable callback;
    private final int delay;
    private final Timer timer;
    private final ActionListener actionListener;

    public PeriodicTask(Runnable callback, int delay) {
        this.callback = callback;
        this.delay = delay;
        this.actionListener = e -> callback.run();

        this.timer = new Timer(delay, actionListener);
        this.timer.setRepeats(true);
    }

    public void start() {
        timer.start();
    }

    public void setDelay(int delay) {
        timer.setDelay(delay);
    }

    public void resume() {
        try {
            if (timer != null) timer.start();
        } catch (Exception ignored) {
        }
    }

    public void stop() {
        timer.stop();
    }
}
