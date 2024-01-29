package game.engine.level.behavior

import game.Bomberman
import game.engine.world.entity.impl.blocks.DestroyableBlock
import game.engine.world.entity.impl.models.Entity

class DespawnDestroyableBlocksBehavior : GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            despawnDestroyableBlocks()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }

    private fun despawnDestroyableBlocks() {
        Bomberman.getMatch()
                .getEntities()
                .stream()
                .filter { entity: Entity? -> entity is DestroyableBlock }
                .forEach { obj: Entity -> obj.despawnAndNotify() }
    }

}
