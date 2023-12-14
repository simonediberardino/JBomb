package game.entity.blocks

import game.entity.models.Block
import game.entity.models.Coordinates
import game.entity.models.Entity

abstract class HardBlock(coordinates: Coordinates?) : Block(coordinates) {
    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> {
        return HashSet()
    }
}
