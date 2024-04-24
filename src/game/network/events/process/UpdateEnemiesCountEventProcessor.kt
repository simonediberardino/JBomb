package game.network.events.process

import game.domain.events.game.UpdateLocalEnemiesCountGameEvent
import game.domain.events.models.HttpEvent
import game.utils.dev.Extensions.getOrTrim

class UpdateEnemiesCountEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        val count = info.getOrTrim("enemiesCount")?.toInt() ?: return

        UpdateLocalEnemiesCountGameEvent().invoke(count)
    }
}