package game.domain.tasks

import game.domain.tasks.GameTickerObserver.Companion.DEFAULT_OBSERVER_UPDATE
import game.domain.tasks.observer.Observable2

/**
 * The GameTickerObservable class is an observable that notifies its observers periodically with a fixed delay
 * of DELAY_MS milliseconds, ignoring updates if a specific delay is not passed. It extends the Observable class.
 */
class GameTickerObservable : Observable2() {
    private val periodicTask: PeriodicTask
    companion object {
        val DELAY_MS: Int = DEFAULT_OBSERVER_UPDATE / 2
    }

    /**
     * This ActionListener updates observers of the GameTickerObservable periodically based on the specified delay. It loops through
     * each observer in the observerSet to check if the delay has passed since the last update. If the delay has passed, it calls the
     * update method of the observer with the current GameState object.
     */
    private val task = Runnable {
        synchronized(observers) {
            for (observer in observers.toTypedArray()) {
                if (observer.isValid()) {
                    notify(observer)
                }
            }
        }
    }

    init {
        periodicTask = PeriodicTask(task, DELAY_MS)
        periodicTask.start()
    }

    fun resume() {
        periodicTask.resume()
    }

    fun stop() {
        periodicTask.stop()
    }
}