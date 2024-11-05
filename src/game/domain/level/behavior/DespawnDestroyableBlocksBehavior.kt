package game.domain.level.behavior

import game.JBomb
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.abstracts.base.Entity

class DespawnDestroyableBlocksBehavior : GameBehavior() {
    override fun hostBehavior() {
        despawnDestroyableBlocks()
    }

    override fun clientBehavior() {}

    private fun despawnDestroyableBlocks() {
        JBomb.match
                .getEntities()
                .stream()
                .filter { entity: Entity? -> entity is DestroyableBlock }
                .forEach { obj: Entity -> obj.logic.eliminated() }
    }

}
