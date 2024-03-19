package game.domain.tasks

import game.domain.tasks.observer.Observable2
import game.domain.tasks.observer.Observer2
import game.utils.Utility
import game.utils.time.now

abstract class GameTickerObserver : Observer2 {
    private var lastUpdate = 0L

    override fun update(arg: Observable2.ObserverParam) {
        lastUpdate = now()
    }

    override fun isValid(): Boolean {
        return Utility.timePassed(lastUpdate) >= delayObserverUpdate // check if the delay has passed since the last update
    }

    open val delayObserverUpdate: Int
        get() {
            return DEFAULT_OBSERVER_UPDATE
        }

    companion object {
        const val DEFAULT_OBSERVER_UPDATE = 30
    }
}