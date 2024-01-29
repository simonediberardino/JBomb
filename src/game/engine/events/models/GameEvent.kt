package game.engine.events.models

interface GameEvent {
    operator fun invoke(arg: Any?)
}