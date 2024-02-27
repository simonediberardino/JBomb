package game.engine.world.domain.entity.actors.impl.bomber_entity.base

import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.engine.world.domain.entity.actors.impl.blocks.HardBlock
import game.engine.world.domain.entity.actors.impl.placeable.Bomb
import game.engine.world.domain.entity.actors.impl.bonus.mystery_box.MysteryBoxPerk
import game.engine.world.domain.entity.actors.impl.models.*
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.logic.IBomberEntityLogic
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityState

abstract class BomberEntity : Character, Explosive {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: IBomberEntityLogic = BomberEntityLogic(entity = this)
    override val state: BomberEntityState = BomberEntityState(entity = this)
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(character = this)
    override val info: EntityInfo = EntityInfo()

    init {
        state.entitiesClassListMouseClick.add(MysteryBoxPerk::class.java)
        image.hitboxSizeToHeightRatio = 0.733f
    }

    // Defines the set of obstacle entities that block the propagation of explosions.
    override val explosionObstacles: Set<Class<out Entity>> = setOf(
            HardBlock::class.java,
            DestroyableBlock::class.java
    )

    // Defines the set of entities that can interact with explosions.
    override val explosionInteractionEntities: Set<Class<out Entity>> = setOf(
            DestroyableBlock::class.java,
            Enemy::class.java,
            Bomb::class.java
    )

    // Defines the maximum distance an explosion can propagate.
    override val maxExplosionDistance: Int
        get() = state.currExplosionLength

    companion object {
        const val MAX_BOMB_CAN_HOLD = 10
        val SPAWN_OFFSET = Coordinates((PitchPanel.GRID_SIZE - Character.DEFAULT.SIZE) / 2, PitchPanel.GRID_SIZE - size)
    }
}
