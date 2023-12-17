package game.tasks

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class PeriodicTaskExecutor(private val callback: Runnable, private val intervalMs: Long) {
    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

    fun scheduleAtFixedRate() {
        scheduler.scheduleAtFixedRate(callback, 0, intervalMs, TimeUnit.MILLISECONDS)
    }

    fun stop() {
        scheduler.shutdown()
    }
}