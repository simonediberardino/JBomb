package game.domain.level.behavior

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.utils.dev.Extensions.toMap
import java.awt.SystemColor.info
import java.util.HashMap

class RespawnDeadPlayersBehavior : GameBehavior() {
    override fun hostBehavior(): () -> Unit = {
        val deadPlayers: HashMap<Long, Pair<Class<out Entity>, Entity>> = JBomb.match.getDeadEntities()

        deadPlayers.forEach { (id, pair) ->
            RespawnDeadPlayerBehavior(
                    id = id,
                    clazz = pair.first,
                    entity = pair.second
            ).invoke()
        }
    }

    override fun clientBehavior(): () -> Unit = {}
}