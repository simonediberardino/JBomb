package game.engine.tasks

import game.engine.tasks.observer.Observer2
import game.utils.time.now

abstract class GameTickerObserver : Observer2 {
    var lastUpdate = 0L
        protected set

    override fun update(arg: Any?) {
        lastUpdate = now()
    }

    open val delayObserverUpdate: Float
        get() {
            return DEFAULT_OBSERVER_UPDATE
        }

    companion object {
        val DEFAULT_OBSERVER_UPDATE = 30f
    }
}