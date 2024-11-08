package game.domain.world.domain.entity.actors.impl.blocks.lava_block.state

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityState

class LavaBlockState(entity: Entity, var canExpand: Boolean = false) : BlockEntityState(entity = entity)