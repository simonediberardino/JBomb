package game.entity.bomb

import game.entity.player.Player
import game.entity.blocks.DestroyableBlock
import game.entity.player.BomberEntity
import game.entity.models.Entity

class EnemyBomb(entity: BomberEntity) : Bomb(entity) {
    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() {
            return setOf(DestroyableBlock::class.java, Player::class.java, Bomb::class.java)
        }
}
