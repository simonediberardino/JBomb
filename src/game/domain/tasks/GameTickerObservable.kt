package game.domain.tasks

import game.domain.tasks.GameTickerObserver.Companion.DEFAULT_OBSERVER_UPDATE
import game.domain.tasks.observer.Observable2
import game.utils.dev.Log
import kotlinx.coroutines.CoroutineScope

/**
 * The GameTickerObservable class is an observable that notifies its observers periodically with a fixed delay
 * of DELAY_MS milliseconds, ignoring updates if a specific delay is not passed. It extends the Observable class.
 */
class GameTickerObservable(private val scope: CoroutineScope) : Observable2() {
    private lateinit var periodicTask: PeriodicTask
    var isRunning = false
        private set

    companion object {
        val DELAY_MS: Long = DEFAULT_OBSERVER_UPDATE
    }

    /**
     * This ActionListener updates observers of the GameTickerObservable periodically based on the specified delay. It loops through
     * each observer in the observerSet to check if the delay has passed since the last update. If the delay has passed, it calls the
     * update method of the observer with the current GameState object.
     */
    private val task = {
        Log.i("[GameTickerObservable] task")

        synchronized(observers) {
            try {
                for (observer in observers.toTypedArray()) {
                    notify(observer, ObserverParam(ObserverParamIdentifier.GAME_TICK, null))
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun start() {
        Log.i("Starting GameTickerObservable")

        periodicTask = PeriodicTask(task, DELAY_MS, scope)
        periodicTask.start()
        isRunning = true
    }

    fun resume() {
        Log.i("Resuming GameTickerObservable")
        periodicTask.resume()
        isRunning = true
    }

    fun stop() {
        Log.i("Stopping GameTickerObservable")

        periodicTask.stop()
        isRunning = false
    }
}