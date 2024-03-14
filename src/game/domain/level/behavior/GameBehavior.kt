package game.domain.level.behavior

import game.Bomberman

abstract class GameBehavior {
    abstract fun hostBehavior(): () -> Unit
    abstract fun clientBehavior(): (() -> Unit)

    fun invoke() {
        if (Bomberman.match.isServer) {
            hostBehavior().invoke()
        } else if (Bomberman.match.isClient) {
            clientBehavior().invoke()
        }
    }
}