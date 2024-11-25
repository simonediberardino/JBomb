package game.domain.world.domain.entity.actors.impl.blocks.lava_block.logic

import game.domain.level.behavior.GameBehavior
import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.blocks.base_block.Block
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.BlockEntityLogic
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.impl.blocks.lava_block.LavaBlock
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext
import kotlin.math.max

class LavaBlockLogic(override val entity: LavaBlock) : BlockEntityLogic(
    entity = entity
) {
    override fun onCollision(e: Entity) {
        super.onCollision(e)

        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior() {
                if (e is Character && e.logic !is AiLogic) {
                    e.logic.onAttackReceived(100, e)
                }
            }

            override fun clientBehavior() {}
        }

        gameBehavior.invoke()
    }

    override fun interactWith(e: Entity?, spawnInteract: Boolean) {
        super.interactWith(e, spawnInteract)

        if (spawnInteract) {
            val gameBehavior: GameBehavior = object : GameBehavior() {
                override fun hostBehavior() {
                    val attackedCharacter = e as? Character
                    attackedCharacter?.logic?.eliminated()
                }

                override fun clientBehavior() {}
            }

            gameBehavior.invoke()
        }
    }

    override fun onSpawn() {
        super.onSpawn()

        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior() {
                val attackedCharacter = Coordinates.getEntitiesOnBlock(entity.info.position) as? Character
                attackedCharacter?.logic?.onAttackReceived(attackedCharacter.state.hp, attackedCharacter)
            }

            override fun clientBehavior() {}
        }

        gameBehavior.invoke()
    }

    override fun onGameStarted() {
        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior() {

                val coordsOnDirection = Direction.values().map { direction ->
                    Coordinates.getNewTopLeftCoordinatesOnDirection(
                        entity.info.position,
                        direction,
                        Block.SIZE
                    )
                }

                val adjBlocksStatus: List<Int> = coordsOnDirection.map { coordOnDirection ->
                    val adjBlock = coordOnDirection.getBlockOnExactCoords()

                    when {
                        adjBlock is DestroyableBlock -> 2 // One adjacent destroyable block is needed
                        adjBlock != null -> 1 // All the other must be end of map borders, or other blocks (lava blocks for example)
                        !coordOnDirection.validate(Block.SIZE) -> 1
                        else -> 0
                    }
                }

                entity.state.canExpand = adjBlocksStatus.any {
                    it == 2
                } && adjBlocksStatus.all { it >= 1 }
            }

            override fun clientBehavior() {}
        }

        gameBehavior.invoke()
    }

    override fun modify(block: Block) {
        if (block is LavaBlock) {
            block.expansionRadius = max(block.expansionRadius, entity.expansionRadius - 1)
        }
    }

    private fun spawnAdjLava() {
        if (!entity.state.canExpand)
            return

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
                            coordinates = coords,
                            canExpand = true
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

        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior() {
                spawnAdjLava()
            }

            override fun clientBehavior() {}
        }

        gameBehavior.invoke()

    }

}