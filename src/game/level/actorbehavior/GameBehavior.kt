package game.level.actorbehavior

interface GameBehavior {
    fun hostBehavior() : () -> Unit
    fun clientBehavior() : (() -> Unit)

    fun invoke() {
        if (true) {
            hostBehavior().invoke()
        } else {
            clientBehavior().invoke()
        }
    }
}