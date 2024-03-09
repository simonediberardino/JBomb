package game.domain.events.models

interface GameEvent {
    operator fun invoke(arg: Any?)
}