package game.domain.events.models

interface GameEvent {
    fun invoke(vararg arg: Any?) {}
}