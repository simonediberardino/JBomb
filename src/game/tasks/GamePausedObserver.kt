package game.tasks

import game.Bomberman
import game.events.models.Observer2
import game.hardwareinput.Command

class GamePausedObserver : Observer2 {
    override fun update(arg: Any?) {
        if (arg !is Command) {
            return
        }
        if (arg === Command.PAUSE) {
            Bomberman.getMatch().toggleGameState()
        }
    }
}