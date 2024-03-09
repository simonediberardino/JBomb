package game.domain.events.models

interface HttpEvent {
    fun invoke(vararg extras: Any)
}