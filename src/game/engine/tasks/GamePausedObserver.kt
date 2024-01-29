package game.engine.tasks

import game.Bomberman
import game.engine.tasks.observer.Observer2
import game.engine.hardwareinput.Command

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