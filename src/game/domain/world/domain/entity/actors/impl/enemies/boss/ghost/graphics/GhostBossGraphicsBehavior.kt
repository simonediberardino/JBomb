package game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.graphics

import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.graphics.BossEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss
import game.utils.file_system.Paths

class GhostBossGraphicsBehavior(override val entity: Boss) : BossEntityGraphicsBehavior(entity = entity) {
    override fun getImageFromRageStatus(): String {
        return java.lang.String.format(
                GhostBoss.SKIN_PATH_TEMPLATE,
                Paths.enemiesFolder,
                entity.state.imageDirection.toString().lowercase(),
                entity.state.currRageStatus
        )
    }
}