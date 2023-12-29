package game.events.models

interface HttpEvent {
    fun invoke(info: Any)
}