package game.domain.level.behavior

import game.Bomberman
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.abstracts.base.Entity

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
