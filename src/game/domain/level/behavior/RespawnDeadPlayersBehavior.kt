package game.domain.level.behavior

import game.Bomberman
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity

class RespawnDeadPlayersBehavior : GameBehavior() {
    override fun hostBehavior(): () -> Unit = {
        val deadPlayers: List<Entity> = Bomberman.match.getDeadEntities()

        deadPlayers.filterIsInstance<BomberEntity>().forEach { deadPlayer ->
            deadPlayer.info.position = Bomberman.match.currentLevel.info.playerSpawnCoordinates
            deadPlayer.logic.spawn(forceSpawn = false, forceCentering = false)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}