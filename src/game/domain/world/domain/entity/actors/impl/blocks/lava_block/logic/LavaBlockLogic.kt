package game.domain.world.domain.entity.actors.impl.blocks.lava_block.logic

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.blocks.base_block.Block
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.BlockEntityLogic
import game.domain.world.domain.entity.actors.impl.blocks.lava_block.LavaBlock
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.utils.dev.Log
import kotlinx.coroutines.isActive
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlin.math.max

class LavaBlockLogic(override val entity: LavaBlock) : BlockEntityLogic(
    entity = entity
) {
    override fun onCollision(e: Entity) {
        super.onCollision(e)

        if (e is Character) {
            e.logic.onAttackReceived(100, e)
        }
    }

    override fun modify(block: Block) {
        if (block is LavaBlock) {
            block.expansionRadius = max(block.expansionRadius, entity.expansionRadius - 1)
        }
    }

    private fun spawnAdjLava() {
        val coords = entity.info.position
        val coordsOnAllDirections: MutableList<Coordinates> = mutableListOf()

        for (d in Direction.values()) {
            coordsOnAllDirections.add(
                Coordinates.getNewTopLeftCoordinatesOnDirection(
                    coords,
                    d,
                    Block.SIZE
                )
            )
        }

        coordsOnAllDirections.forEach { coords ->
            coords.getBlockOnExactCoords().also { block ->
                block ?: run {
                    val newRadius = entity.expansionRadius - 1
                    if (newRadius > 0) {
                        LavaBlock(
                            hasSpawnDelay = true,
                            coordinates = coords
                        ).also {
                            it.expansionRadius = newRadius
                        }
                    }
                }
            }
        }
    }

    override suspend fun onTrigger() {
        if (!coroutineContext.isActive) {
            return
        }

        spawnAdjLava()
    }

}