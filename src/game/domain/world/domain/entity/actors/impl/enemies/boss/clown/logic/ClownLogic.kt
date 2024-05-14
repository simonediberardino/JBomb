package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.logic

import game.JBomb
import game.audio.SoundModel
import game.domain.level.behavior.GameBehavior
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.logic.BossEntityLogic
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.Clown
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.hat.Hat
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb
import game.domain.world.domain.entity.actors.impl.enemies.npcs.clown_nose.ClownNose
import game.domain.world.domain.entity.actors.impl.explosion.ConfettiExplosion
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.explosion.handler.ExplosionHandler
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.domain.entity.geo.EnhancedDirection
import game.presentation.ui.panels.game.PitchPanel
import game.utils.Utility
import game.utils.time.now

class ClownLogic(
        override val entity: Clown
) : BossEntityLogic(entity = entity), IClownLogic {
    private val spawnTimeAttackDelay = 10_000L

    override fun onHit(damage: Int) {
        super.onHit(damage)
        // Get the current health percentage of the Boss.
        val hpPercentage = entity.state.hpPercentage
        // Get the entry from the health status map whose key is the lowest greater than or equal to hpPercentage.
        val entry = entity.properties.healthStatusMap?.ceilingEntry(hpPercentage) ?: return

        // If there is an entry, update the rage status of the Boss.
        updateRageStatus(entry.value)
    }

    override fun onSpawn() {
        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior(): () -> Unit = {
                val panelSize = JBomb
                        .JBombFrame
                        .pitchPanel
                        .preferredSize

                val y = panelSize.getHeight().toInt() - entity.state.size
                val x = (panelSize.getWidth() / 2 - entity.state.size / 2).toInt()

                entity.logic.move(Coordinates(x, y))
            }

            override fun clientBehavior(): () -> Unit = {}

        }
        gameBehavior.invoke()

        JBomb.match.gameTickerObservable?.register(entity)
    }

    /**
     * Spawns orbs in all directions around entity.
     */
    override fun spawnOrbs() {
        Direction.values().forEach { d ->
            ClownNose(Coordinates.fromDirectionToCoordinateOnEntity(
                    entity,
                    d,
                    Orb.DEFAULT.SIZE,
                    Orb.DEFAULT.SIZE
            ), d
            ).logic.spawn(forceSpawn = true, forceCentering = false)
        }
    }

    /**
     * Spawns enhanced orbs in all enhanced directions around entity.
     */
    override fun spawnEnhancedOrbs() {
        EnhancedDirection.values().forEach { d ->
            ClownNose(Coordinates.fromDirectionToCoordinateOnEntity(
                    entity,
                    d,
                    Orb.DEFAULT.SIZE
            ), d).logic.spawn(forceSpawn = true, forceCentering = false)
        }
    }

    /**
     * Calculates the explosion offsets based on the given direction.
     *
     * @param d the direction
     * @return the explosion offsets as an int array
     */
    override fun calculateExplosionOffsets(d: Direction): IntArray {
        var inwardOffset = Character.size / 4
        var parallelOffset = -AbstractExplosion.SIZE / 2
        when (d) {
            Direction.RIGHT, Direction.LEFT -> {
                parallelOffset = 0
                inwardOffset = Character.size / 3 - PitchPanel.GRID_SIZE / 2
            }

            else -> {}
        }
        return intArrayOf(inwardOffset, parallelOffset)
    }

    /**
     * Spawns an explosion in a random direction.
     */
    override fun spawnExplosion() {
        val directions = Direction.values().toMutableList()
        directions.remove(Direction.DOWN)

        val d = directions[(Math.random() * directions.size).toInt()]
        val offsets = calculateExplosionOffsets(d)

        val explosionCoordinates = Coordinates.fromDirectionToCoordinateOnEntity(
                entity,
                d,
                offsets[0],
                offsets[1],
                AbstractExplosion.SIZE
        )

        ExplosionHandler.instance.process(SoundModel.EXPLOSION_CONFETTI) {
            listOf(
                    ConfettiExplosion(
                            owner = entity,
                            coordinates = explosionCoordinates,
                            direction = d,
                            explosive = entity
                    ).logic.explode()
            )
        }
    }

    /**
     * Throws a hat in a random enhanced direction.
     */
    override fun throwHat() {
        val direction = EnhancedDirection.randomDirectionTowardsCenter(entity)
        val coordinates = Coordinates.fromDirectionToCoordinateOnEntity(entity, direction, 0)
        val hat: Entity = Hat(coordinates, direction)

        hat.logic.spawn(forceSpawn = true, forceCentering = false)
        entity.state.hasHat = false
    }


    /**
     * Checks if the clown boss should spawn an explosion and does so if the conditions are met.
     */
    override fun checkAndSpawnExplosion() {
        if (Utility.timePassed(entity.state.lastAttackTime) <= entity.state.attackDelay) return
        // Run a percentage-based chance for spawning an explosion
        Utility.runPercentage(SHOOTING_CHANCE) {
            entity.state.lastAttackTime = now()
            spawnExplosion()
        }
    }

    /**
     * Checks if the clown boss should spawn orbs and does so if the conditions are met.
     */
    override fun checkAndSpawnOrbs() {
        if (Utility.timePassed(entity.state.lastAttackTime) <= entity.state.attackDelay) return

        // Run a percentage-based chance for spawning enhanced and regular orbs
        Utility.runPercentage(SHOOTING_CHANCE) {
            entity.state.lastAttackTime = now()
            spawnEnhancedOrbs()
            spawnOrbs()
        }
    }

    /**
     * Checks if the clown boss should throw a hat and does so if the conditions are met.
     */
    override fun checkAndThrowHat() {
        if (Utility.timePassed(entity.state.lastAttackTime) <= entity.state.attackDelay || !entity.state.hasHat) return
        // Run a percentage-based chance for throwing a hat
        Utility.runPercentage(SHOOTING_CHANCE) {
            entity.state.lastAttackTime = now()
            throwHat()
        }
    }

    /**
     * @return A boolean value representing whether the Clown entity is wearing a hat or not.
     */
    fun isHatImage(path: String): Boolean {
        val tokens = path.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (tokens.size <= 1) false else tokens[1] == "1"
    }

    override fun process() {
        super.process()

        if (Utility.timePassed(entity.state.spawnTime) < spawnTimeAttackDelay)
            return

        // Check and potentially spawn explosions
        checkAndSpawnExplosion()

        // Check and potentially spawn orbs
        checkAndSpawnOrbs()

        // Check and potentially throw a hat
        checkAndThrowHat()
    }

    companion object {
        const val SHOOTING_CHANCE = 3
    }
}