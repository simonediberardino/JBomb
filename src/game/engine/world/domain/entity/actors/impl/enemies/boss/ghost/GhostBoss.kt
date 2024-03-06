package game.engine.world.domain.entity.actors.impl.enemies.boss.ghost

import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.graphics.BossEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.properties.BossEntityProperties
import game.engine.world.domain.entity.actors.impl.enemies.boss.ghost.graphics.GhostBossGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.enemies.boss.ghost.graphics.GhostBossImageModel
import game.engine.world.domain.entity.actors.impl.enemies.boss.ghost.logic.GhostBossLogic
import game.engine.world.domain.entity.actors.impl.enemies.boss.ghost.state.GhostBossState
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.types.EntityTypes
import java.util.*

class GhostBoss : Boss {
    constructor() : super()
    constructor(id: Long) : super(id)

    override val info: EntityInfo = EntityInfo()
    override val state: GhostBossState = GhostBossState(entity = this)
    override val logic: GhostBossLogic = GhostBossLogic(entity = this)
    override val image: GhostBossImageModel = GhostBossImageModel(entity = this)
    override val graphicsBehavior: GhostBossGraphicsBehavior = GhostBossGraphicsBehavior(entity = this)
    override val properties: BossEntityProperties = BossEntityProperties(types = EntityTypes.GhostBoss)

    companion object {
        const val SKIN_PATH_TEMPLATE = "%s/ghost_boss/ghost_with_axe_%s_%s.png"
    }

    internal object DEFAULT {
        val IMAGE_DIRECTIONS = listOf(Direction.RIGHT, Direction.LEFT)
        val HEALTH_STATUS_MAP = TreeMap<Int, Int>()
        val HITBOX_SIZE_TO_WIDTH_RATIO = 0.648f
        val HITBOX_SIZE_TO_HEIGHT_RATIO = 0.70f
    }
}
