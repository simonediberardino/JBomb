package game.domain.world.domain.entity.actors.impl.blocks.lava_block.properties

import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityProperties
import game.domain.world.types.EntityTypes

class LavaEntityProperties(
    val hasSpawnDelay: Boolean
): BlockEntityProperties(type = EntityTypes.LavaBlock) {
}