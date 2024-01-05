package game.events.models

interface HttpEvent {
    fun invoke(vararg extras: Any)
}