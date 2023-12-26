package game.actorbehavior

import game.Bomberman

interface GameBehavior {
    fun hostBehavior(): () -> Unit
    fun clientBehavior(): (() -> Unit)

    fun invoke() {
        if (Bomberman.getMatch().isServer) {
            hostBehavior().invoke()
        } else if (Bomberman.getMatch().isClient) {
            clientBehavior().invoke()
        }
    }
}