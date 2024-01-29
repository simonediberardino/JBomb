package game.engine.tasks

import game.Bomberman
import game.engine.world.entity.impl.models.Entity
import game.engine.tasks.observer.Observable2
import game.utils.Utility.timePassed

/**
 * The GameTickerObservable class is an observable that notifies its observers periodically with a fixed delay
 * of DELAY_MS milliseconds, ignoring updates if a specific delay is not passed. It extends the Observable class.
 */
class GameTickerObservable : Observable2() {
    private val periodicTask: PeriodicTask
    private val DELAY_MS = 20

    /**
     * This ActionListener updates observers of the GameTickerObservable periodically based on the specified delay. It loops through
     * each observer in the observerSet to check if the delay has passed since the last update. If the delay has passed, it calls the
     * update method of the observer with the current GameState object.
     */
    private val task = Runnable {
        val array = observers.toTypedArray()

        // loop through each observer in the observerSet
        for (observer in array) {
            if (observer is Entity && !observer.isSpawned) unregister(observer)
            var delayPassed = true
            if (observer is GameTickerObserver) { // check if the observer is of type GameTickerObserver
                // cast the observer to GameTickerObserver
                val lastUpdate: Long = observer.lastUpdate // get the last update time of the observer
                val delayObserverUpdate: Long = observer.delayObserverUpdate.toLong() // get the delay time of the observer
                delayPassed = timePassed(lastUpdate) >= delayObserverUpdate // check if the delay has passed since the last update
            }
            if (delayPassed) { // if the delay has passed
                notify(observer, Bomberman.getMatch().gameState)
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