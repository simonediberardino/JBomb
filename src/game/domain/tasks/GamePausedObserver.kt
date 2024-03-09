package game.domain.tasks

import game.Bomberman
import game.domain.tasks.observer.Observer2
import game.input.Command

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