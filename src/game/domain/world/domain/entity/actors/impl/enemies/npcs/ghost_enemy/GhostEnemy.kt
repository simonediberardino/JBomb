package game.domain.world.domain.entity.actors.impl.enemies.npcs.ghost_enemy

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.domain.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ghost_enemy.properties.GhostEnemyProperties
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ghost_enemy.properties.GhostEnemyState
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.types.EntityTypes
import game.presentation.ui.panels.game.PitchPanel
import game.utils.file_system.Paths.enemiesFolder

class GhostEnemy : AiEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)

    override val image: CharacterImageModel = object : CharacterImageModel(
            entity = this
    ) {
        override var hitboxSizeToHeightRatio = GhostEnemy.DEFAULT.HITBOX_SIZE_TO_HEIGHT_RATIO
        override var hitboxSizeToWidthRatio = GhostEnemy.DEFAULT.HITBOX_SIZE_TO_WIDTH_RATIO

        override fun characterOrientedImages(): Array<String> {
            return arrayOf("$enemiesFolder/mini_ghost/ghost_${state.imageDirection.toString().lowercase()}.png")
        }
    }
    
    override val properties: GhostEnemyProperties = GhostEnemyProperties(types = EntityTypes.GhostEnemy)
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)
    override val state: GhostEnemyState = GhostEnemyState(entity = this)

    internal object DEFAULT {
        val WHITELIST_OBSTACLES: MutableSet<Class<out Entity>>
            get() = mutableSetOf(DestroyableBlock::class.java)
        val SIZE = PitchPanel.COMMON_DIVISOR * 2
        val IMAGE_DIRECTIONS: List<Direction>
            get() = listOf(Direction.RIGHT, Direction.LEFT)
        val HITBOX_SIZE_TO_WIDTH_RATIO = 0.837f
        val HITBOX_SIZE_TO_HEIGHT_RATIO = 1f
    }
}