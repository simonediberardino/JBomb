package game.domain.level.behavior

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.utils.dev.Extensions.toMap

class RespawnDeadPlayerBehavior(
        private val id: Long,
        private val clazz: Class<out Entity>,
        private val entity: Entity
) : GameBehavior() {
    override fun hostBehavior(): () -> Unit = {
        val info = entity.toEntityNetwork().toMap()
        val newEntity = clazz.getConstructor(Long::class.java).newInstance(id)
        newEntity.info.position = JBomb.match.currentLevel.info.playerSpawnCoordinates
        newEntity.updateInfo(info)
        newEntity.logic.spawn(forceSpawn = false, forceCentering = false)
    }

    override fun clientBehavior(): () -> Unit = {}

}