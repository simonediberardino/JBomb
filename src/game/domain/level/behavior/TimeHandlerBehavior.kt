package game.domain.level.behavior

import game.JBomb
import game.network.events.forward.TimeHttpEventForwarder
import game.utils.dev.Log

class TimeHandlerBehavior(val time: Long): GameBehavior() {
    override fun hostBehavior() {
        val gameState = JBomb.match.gameState

        if (gameState) {
            baseBehavior()
            notifyClient()
        } else {
            Log.i("Timer not processed: game ended")
        }
    }

    override fun clientBehavior() {
        baseBehavior()
    }

    private fun notifyClient() {
        TimeHttpEventForwarder().invoke(time)
    }

    private fun baseBehavior() {
        JBomb.match.onTimeUpdate(time)
        JBomb.match.currentLevel.eventHandler.onTimeUpdate(time)
    }
}