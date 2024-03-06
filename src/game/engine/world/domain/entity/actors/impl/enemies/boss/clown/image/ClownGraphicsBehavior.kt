package game.engine.world.domain.entity.actors.impl.enemies.boss.clown.image

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.graphics.BossEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.Clown
import game.utils.file_system.Paths

class ClownGraphicsBehavior(override val entity: Clown) : BossEntityGraphicsBehavior(entity = entity) {
    override fun getImageFromRageStatus(): String =
            String.format(Clown.SKIN_PATH_TEMPLATE, Paths.enemiesFolder, if (entity.state.hasHat) 1 else 0, entity.state.currRageStatus)

    override fun getHitboxSizeToHeightRatio(entity: Entity, path: String): Float {
        entity.image.hitboxSizeToHeightRatio = if (this.entity.logic.isHatImage(path)) Clown.DEFAULT.RATIO_HEIGHT_WITH_HAT else Clown.DEFAULT.RATIO_HEIGHT
        return entity.image.hitboxSizeToHeightRatio
    }
}