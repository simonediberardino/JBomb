package game.network.events.process

import game.JBomb
import game.domain.events.models.HttpEvent
import game.domain.level.behavior.TimeHandlerBehavior
import game.domain.world.domain.entity.actors.abstracts.placeable.bomb.Bomb
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class TimeHttpEventProcessor: HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val time = info.getOrTrim("time")?.toLong() ?: return

        Log.i("TimeHttpEventProcessor $time")
        TimeHandlerBehavior(time).invoke()
    }
}