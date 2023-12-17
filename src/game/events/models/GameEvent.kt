package game.events.models

interface GameEvent {
    operator fun invoke(arg: Any?)
}