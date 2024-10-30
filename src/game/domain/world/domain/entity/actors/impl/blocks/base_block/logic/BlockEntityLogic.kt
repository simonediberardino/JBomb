package game.domain.world.domain.entity.actors.impl.blocks.base_block.logic

import game.JBomb
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.logic.EntityLogic
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.impl.blocks.base_block.Block
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.coroutineContext

open class BlockEntityLogic(entity: Entity) : EntityLogic(entity), IBlockEntityLogic {
    override fun destroy() {
        entity.logic.eliminated()
    }

    override fun onRemoved() {
        super.onRemoved()
        JBomb.match.scope.launch {
            trigger()
        }
    }

    override suspend fun trigger(updatedBlocks: MutableList<Block>) {
        val queue: Queue<Block> = LinkedList()
        val visitedBlocks = mutableSetOf<Block>() // Track visited blocks to avoid re-processing

        queue.add(entity as Block)
        visitedBlocks.add(entity as Block)

        while (queue.isNotEmpty() && coroutineContext.isActive) {
            delay(500L)

            val currBlock = queue.remove() // Get the current block and its radius level

            // Trigger the current block and add it to updated blocks
            currBlock.logic.onTrigger()
            updatedBlocks.add(currBlock)

            // If the current radius is greater than zero, proceed with adjacent blocks
            val adjBlocks = currBlock.info.position.getAdjBlocks()

            adjBlocks.forEach { adjBlock ->
                if (adjBlock !in visitedBlocks) { // Process each unvisited adjacent block
                    visitedBlocks.add(adjBlock)
                    queue.add(adjBlock) // Enqueue with reduced radius
                }
            }
        }
    }


    fun Coordinates.getAdjBlocks(): LinkedList<Block> {
        val coordsOnAllDirections: MutableList<Coordinates> = mutableListOf()

        for (d in Direction.values()) {
            coordsOnAllDirections.add(Coordinates.getNewTopLeftCoordinatesOnDirection(this, d, Block.SIZE))
        }

        return LinkedList(coordsOnAllDirections.mapNotNull {
            it.getBlockOnExactCoords()
        })
    }

    fun Coordinates.getBlockOnExactCoords(): Block? {
        val entities = JBomb.match.getEntities()
        return entities.firstOrNull { e: Entity ->
            e is Block
                    && Coordinates.doesCollideWith(
                this,
                e
            )
        } as Block?
    }


    override fun damageAnimation() {}

    override fun onAttackReceived(damage: Int, attacker: EntityInteractable) {
        destroy()
    }

    override fun observerUpdate(arg: Observable2.ObserverParam) {}

    override fun interactWith(e: Entity?) {}

    override fun doInteractWith(e: Entity?) {}


}