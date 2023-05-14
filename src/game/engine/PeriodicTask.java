package game.engine;

import javax.swing.*;

public class PeriodicTask {
    private final Runnable callback;
    private final int delay;
    private final Thread thread;

    public PeriodicTask(Runnable callback, int delay){
        this.callback = callback;
        this.delay = delay;
        this.thread = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    continue;
                }
                SwingUtilities.invokeLater(callback);
            }
        });
    }

    public void start() {
        thread.start();
    }

    public void resume() {
        try{
            if(thread != null) thread.resume();
        }catch (Exception ignored){}
    }

    public void stop() {
        try{
            if(thread != null) thread.suspend();
        }catch (Exception ignored){}
    }
}
