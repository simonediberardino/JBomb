package game.domain.tasks

import game.domain.tasks.observer.Observable2
import game.domain.tasks.observer.Observer2
import game.utils.time.now
import game.utils.time.timeunit

abstract class GameTickerObserver : Observer2 {
    var lastUpdate = 0L
        private set

    override fun update(arg: Observable2.ObserverParam) {
        lastUpdate = now()
    }

    companion object {
        val DEFAULT_OBSERVER_UPDATE = timeunit() * 8
    }
}