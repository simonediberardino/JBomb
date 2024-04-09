package game.domain.tasks

import game.domain.tasks.GameTickerObserver.Companion.DEFAULT_OBSERVER_UPDATE
import game.domain.tasks.observer.Observable2
import kotlinx.coroutines.CoroutineScope

/**
 * The GameTickerObservable class is an observable that notifies its observers periodically with a fixed delay
 * of DELAY_MS milliseconds, ignoring updates if a specific delay is not passed. It extends the Observable class.
 */
class GameTickerObservable(private val scope: CoroutineScope) : Observable2() {
    private val periodicTask: PeriodicTask
    companion object {
        val DELAY_MS: Long = DEFAULT_OBSERVER_UPDATE
    }

    /**
     * This ActionListener updates observers of the GameTickerObservable periodically based on the specified delay. It loops through
     * each observer in the observerSet to check if the delay has passed since the last update. If the delay has passed, it calls the
     * update method of the observer with the current GameState object.
     */
    private val task = {
        synchronized(observers) {
            for (observer in observers.toTypedArray()) {
                notify(observer, ObserverParam(ObserverParamIdentifier.GAME_TICK, null))
            }
        }
    }

    init {
        periodicTask = PeriodicTask(task, DELAY_MS, scope)
        periodicTask.start()
    }

    fun resume() = periodicTask.resume()

    fun stop() = periodicTask.stop()
}