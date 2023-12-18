package game.level.functionalities

import game.Bomberman
import game.entity.blocks.DestroyableBlock
import game.entity.models.Entity
import game.level.Level
import game.multiplayer.HostBehavior

class DespawnDestroyableBlocks : LevelUseCase {
    override fun invoke() {
        val hostBehavior = object: HostBehavior {
            override fun executeHostLogic() {
                despawnDestroyableBlocks()
            }
        }

        hostBehavior.executeHostLogic()
    }

    fun despawnDestroyableBlocks() {
        Bomberman.getMatch()
                .entities
                .stream()
                .filter { entity: Entity? -> entity is DestroyableBlock }
                .forEach { obj: Entity -> obj.despawn() }
    }
}