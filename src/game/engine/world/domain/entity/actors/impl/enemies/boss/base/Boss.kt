package game.engine.world.domain.entity.actors.impl.enemies.boss.base

import game.Bomberman
import game.engine.level.behavior.GameBehavior
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.sound.SoundModel
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.graphics.BossEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.logic.BossEntityLogic
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.properties.BossEntityProperties
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.properties.BossEntityState
import game.values.DrawPriority

/**
 * An abstract class for enemy bosses;
 */
abstract class Boss : AiEnemy {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    constructor() : this(null) {
        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior(): () -> Unit {
                return {
                    logic.move((Coordinates.randomCoordinatesFromPlayer(state.size, state.size * 2)))
                }
            }

            override fun clientBehavior(): () -> Unit {
                return {}
            }

        }
        gameBehavior.invoke()
    }

    abstract override val logic: BossEntityLogic
    abstract override val state: BossEntityState
    abstract override val properties: BossEntityProperties
    abstract override val graphicsBehavior: BossEntityGraphicsBehavior

    internal object DEFAULT {
        val DRAW_PRIORITY = DrawPriority.DRAW_PRIORITY_3
        val ATTACK_DAMAGE = 1000
        val SIZE = PitchPanel.GRID_SIZE * 4
        val MAX_HP: Int by lazy {
            Bomberman.getMatch().currentLevel!!.info.bossMaxHealth
        }
        val OBSTACLES: Set<Class<out Entity?>?> = emptySet()
        val DEATH_SOUND = SoundModel.BOSS_DEATH
        val HEALTH_STATUS_MAP = hashMapOf<Int, Int>()
        val START_RAGE_STATUS = 0
    }
}
