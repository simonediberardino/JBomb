package game.domain.level.behavior

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity

class RespawnDeadPlayersBehavior : GameBehavior() {
    override fun hostBehavior(): () -> Unit = {
        val deadPlayers: HashMap<Long, Class<out Entity>> = JBomb.match.getDeadEntities()

        deadPlayers.forEach { (id, clazz) ->
            val entity = clazz.getConstructor(Long::class.java).newInstance(id)
            entity.info.position = JBomb.match.currentLevel.info.playerSpawnCoordinates
            entity.logic.spawn(forceSpawn = false, forceCentering = false)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}