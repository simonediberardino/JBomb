package game.engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PeriodicTask {
    private final Runnable callback;
    private final int delay;
   // private final Thread thread;
    private final Timer timer;

    public PeriodicTask(Runnable callback, int delay) {
        this.callback = callback;
        this.delay = delay;
        this.timer = new Timer(delay, e -> SwingUtilities.invokeLater(callback));
    }

    public void start() {
        timer.start();
    }

    public void setDelay(int delay){
        timer.setDelay(delay);
    }

    public void resume() {
        try{
            if(timer != null) timer.start();
        }catch (Exception ignored){}
    }

    public void stop() {
        try{
            if(timer != null) timer.stop();
        }catch (Exception ignored){}
    }
}
