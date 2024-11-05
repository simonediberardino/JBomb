package game.domain.level.behavior

import game.JBomb

abstract class GameBehavior {
    abstract fun hostBehavior()
    abstract fun clientBehavior()

    fun invoke() {
        if (JBomb.match.isServer) {
            hostBehavior()
        } else if (JBomb.match.isClient) {
            clientBehavior()
        }
    }
}