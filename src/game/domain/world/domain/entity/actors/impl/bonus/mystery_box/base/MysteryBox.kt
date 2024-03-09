package game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base

import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.level.levels.Level
import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.logic.MysteryBoxLogic
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.state.MysteryBoxState
import game.utils.file_system.Paths
import java.awt.image.BufferedImage

abstract class MysteryBox(
        level: Level,
        buyer: Entity?
) : HardBlock(Coordinates(0, 0)) {
    init {
        state.level = level
        state.buyer = buyer
    }

    abstract override val state: MysteryBoxState
    abstract override val logic: MysteryBoxLogic

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage {
            return loadAndSetImage(entity, "${Paths.powerUpsFolder}/box_${state.status}.png")
        }
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.MysteryBoxPerk)

    enum class Status {
        CLOSED,
        OPEN
    }

    companion object {
        const val OPEN_BOX_TIME = 5000
        const val CONFIRM_DELAY_MS: Long = 5000
    }

    internal object DEFAULT {
        val SIZE = PitchPanel.GRID_SIZE
    }
}
