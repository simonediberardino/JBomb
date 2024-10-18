package game.domain.level.behavior

import game.JBomb
import game.network.events.forward.TimeHttpEventForwarder

class TimeHandlerBehavior(val time: Long): GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            baseBehavior()
            notifyClient()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {
            baseBehavior()
        }
    }

    private fun notifyClient() {
        TimeHttpEventForwarder().invoke(time)
    }

    private fun baseBehavior() {
        JBomb.match.onTimeUpdate(time)
        JBomb.match.currentLevel.eventHandler.onTimeUpdate(time)
    }
}