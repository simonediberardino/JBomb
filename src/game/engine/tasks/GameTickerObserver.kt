package game.engine.tasks

import game.engine.tasks.observer.Observer2

abstract class GameTickerObserver : Observer2 {
    var lastUpdate = 0L
        protected set

    override fun update(arg: Any?) {
        lastUpdate = System.currentTimeMillis()
    }

    open val delayObserverUpdate: Float
        get() {
            return 30f
        }
}