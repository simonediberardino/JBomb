package game.engine.events.models

interface HttpEvent {
    fun invoke(vararg extras: Any)
}