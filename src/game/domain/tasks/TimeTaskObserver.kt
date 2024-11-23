package game.domain.tasks

import game.JBomb
import game.domain.level.behavior.TimeHandlerBehavior
import game.domain.tasks.observer.Observable2
import game.domain.tasks.observer.Observer2
import game.utils.Utility
import game.utils.time.now
import kotlinx.coroutines.launch

class TimeTaskObserverAndObservable : Observer2 {
    private var lastTimeUpdate: Long = 0
    private var lastNotifiedTime: Long = 0

    override fun update(arg: Observable2.ObserverParam) {
        if (JBomb.match.currentLevel.info.timeLimitMinutes <= 0 || !JBomb.match.currentLevel.info.isTimeEnabled)
            return

        if (Utility.timePassed(lastTimeUpdate) > 1_000) {
            lastTimeUpdate = now()
            lastNotifiedTime += 1000

            JBomb.match.scope.launch {
                TimeHandlerBehavior(lastNotifiedTime).invoke()
            }
        }
    }
}