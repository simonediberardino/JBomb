package game.domain.world.domain.entity.actors.impl.enemies.boss.base

import game.JBomb
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.audio.SoundModel
import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.graphics.BossEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.logic.BossEntityLogic
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.properties.BossEntityProperties
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.properties.BossEntityState
import game.values.DrawPriority
import java.util.TreeMap

/**
 * An abstract class for enemy bosses;
 */
abstract class Boss : AiEnemy {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    constructor() : this(null) {}

    abstract override val logic: BossEntityLogic
    abstract override val state: BossEntityState
    abstract override val properties: BossEntityProperties
    abstract override val graphicsBehavior: BossEntityGraphicsBehavior

    internal object DEFAULT {
        val DRAW_PRIORITY = DrawPriority.DRAW_PRIORITY_4
        val ATTACK_DAMAGE = 1000
        val SIZE = PitchPanel.GRID_SIZE * 4
        val MAX_HP: Int by lazy {
            JBomb.match.currentLevel.info.bossMaxHealth
        }
        val OBSTACLES: Set<Class<out Entity>>
            get() = emptySet()
        val DEATH_SOUND = SoundModel.BOSS_DEATH
        val HEALTH_STATUS_MAP: TreeMap<Int, Int>
            get() = TreeMap<Int, Int>()
        val START_RAGE_STATUS = 0
    }
}
