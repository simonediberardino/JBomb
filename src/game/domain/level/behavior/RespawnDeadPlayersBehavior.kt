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
            val clazz = pair.first
            // converts the entity to a map to update the infos, better way???
            // converting to entity network in domain?
            val info = pair.second.toEntityNetwork().toMap()

            val entity = clazz.getConstructor(Long::class.java).newInstance(id)
            entity.info.position = JBomb.match.currentLevel.info.playerSpawnCoordinates
            entity.updateInfo(info)
            entity.logic.spawn(forceSpawn = false, forceCentering = false)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}