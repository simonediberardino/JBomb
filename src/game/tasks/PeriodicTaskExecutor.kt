package game.tasks;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class PeriodicTaskExecutor {
    private final ScheduledExecutorService scheduler;
    private final long intervalMs;
    private final Runnable callback;

    public PeriodicTaskExecutor(Runnable callback, long intervalMs) {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.callback = callback;
        this.intervalMs = intervalMs;
    }

    public void scheduleAtFixedRate() {
        scheduler.scheduleAtFixedRate(callback, 0, intervalMs, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}
