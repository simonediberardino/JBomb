package game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.logic

import game.JBomb
import game.audio.AudioManager
import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.logic.BossEntityLogic
import game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ghost_enemy.GhostEnemy
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.presentation.ui.panels.game.PitchPanel
import game.utils.Utility
import game.utils.dev.Log
import game.utils.time.now
import game.utils.ui.GradientCallbackHandler
import java.awt.event.ActionEvent
import java.util.function.Consumer
import javax.swing.SwingUtilities
import javax.swing.Timer

class GhostBossLogic(override val entity: GhostBoss) : BossEntityLogic(entity = entity), IGhostBossLogic {
    override fun attackAnimationAndSoundFX() {
        if (entity.state.currRageStatus == 1)
            return

        AudioManager.instance.play(SoundModel.AXE_HIT)
        updateRageStatus(1)

        val t = Timer(ATTACK_RESET_DELAY) { _: ActionEvent? ->
            updateRageStatus(0)
        }

        t.isRepeats = false
        t.start()
    }

    /**
     * This method makes the Ghost disappear and then reappear by changing its alpha value gradually.
     */
    override fun disappearAndReappear() {
        synchronized((lock2 as Any)) {
            Log.e("Running disappear = ${!entity.state.isInvisibleTaskRunning}")
            if (entity.state.isInvisibleTaskRunning) {
                // If an invisible task is already running, exit the method.
                return
            }

            // Check if enough time has passed since the last invisibility to start the task.
            if (Utility.timePassed(entity.state.lastInvisibleTime) < INVISIBLE_DELAY) {
                return
            }

            // Set the flag to indicate that an invisible task is running.
            entity.state.isInvisibleTaskRunning = true

            // Define the start and end alpha values, and the step for alpha increment/decrement.
            val startAlpha = 1f
            val endAlpha = 0f
            val step = 0.01f

            // Create a callback handler for the task of gradually showing the object.
            val showTask = GradientCallbackHandler(endAlpha, startAlpha, step) {
                // Set the alpha value of the object to the given parameter value.
                entity.state.alpha = it
                if (it + step >= startAlpha) {
                    // Record the current time as the last invisibility time.
                    entity.state.lastInvisibleTime = now()

                    // If the alpha value has reached or exceeded the start alpha, mark the invisible task as finished.
                    entity.state.isInvisibleTaskRunning = false
                }
            }

            // Create a callback handler for the task of gradually hiding the object.
            val hideTask = GradientCallbackHandler(startAlpha, endAlpha, step) {
                // Set the alpha value of the object to the given parameter value.
                entity.state.alpha = it

                if (it > endAlpha + step) {
                    // If the alpha value is not yet less than or equal to the end alpha, exit the method.
                    return@GradientCallbackHandler
                }

                // Create a timer to delay the execution of the show task after the object is hidden.
                val t = Timer(INVISIBLE_DURATION) { _: ActionEvent? -> showTask.execute() }
                t.isRepeats = false
                t.start()
            }

            // Start the task of gradually hiding the object.
            hideTask.execute()
        }
    }

    override fun spawnGhosts(n: Int) {
        if (Utility.timePassed(entity.state.lastGhostSpawnTime) < GHOST_SPAWN_TIME_DELAY)
            return

        entity.state.lastGhostSpawnTime = now()

        for (i in 0 until n) {
            if (JBomb.match.enemiesAlive >= MAX_GHOSTS_ALIVE) return
            val ghostEnemy = GhostEnemy()
            val randomCoordinates = Coordinates.randomCoordinatesFromPlayer(ghostEnemy.state.size)
            ghostEnemy.logic.move(randomCoordinates)
            ghostEnemy.logic.spawn()
        }
    }

    override fun attack(e: Entity?) {
        attackAnimationAndSoundFX()
        super.attack(e)
    }

    override fun process() {
        Utility.runPercentage(ACTION_CHANCE) { this.attack() }
        Utility.runPercentage(ACTION_CHANCE) { disappearAndReappear() }

        Utility.runPercentage(ACTION_CHANCE) {
            if (Utility.timePassed(entity.state.lastLightsEvent) <= LIGHTS_EVENT_DELAY) {
                return@runPercentage
            }
            performLightsAnimation()
            entity.state.lastLightsEvent = now()
        }

        Utility.runPercentage(ACTION_CHANCE) {
            val enemyCount = (Math.random() * MAX_GHOST_ENEMY_SPAWNED).toInt() //spawn ghost enemies
            spawnGhosts(enemyCount)
        }

        super.process()
    }

    private fun attack() {
        val coordsOfUnderneathEntityBlocks = Coordinates.getAllBlocksInAreaFromDirection(entity, Direction.DOWN, BOSS_ATTACK_VERTICAL_RANGE)
        val coordsOfEntitysImageDirectionBlocks = Coordinates.getAllBlocksInAreaFromDirection(
                entity,
                entity.state.imageDirection,
                BOSS_ATTACK_HORIZONTAL_RANGE
        )
        coordsOfUnderneathEntityBlocks.addAll(coordsOfEntitysImageDirectionBlocks)

        //merge the 2 lists into one another
        coordsOfUnderneathEntityBlocks.forEach(Consumer { c: Coordinates? -> Coordinates.getEntitiesOnBlock(c).forEach(this::interact) })
        attackAnimationAndSoundFX()
    }

    override fun performLightsAnimation() {
        synchronized((lock1 as Any)) {
            SwingUtilities.invokeLater {
                val timer = Timer(0, null)
                var count = 0
                timer.addActionListener { e: ActionEvent? ->
                    val rand = (Math.random() * 10000).toInt()
                    when (count) {
                        0, 2, 4 -> PitchPanel.turnOffLights()
                        1, 3 -> PitchPanel.turnOnLights()
                    }
                    timer.setDelay(rand)
                    if (count >= 5 || JBomb.isGameEnded) {
                        PitchPanel.turnOnLights()
                        timer.stop()
                    }
                    count++
                }
                timer.initialDelay = 0
                timer.start()
            }
        }
    }

    companion object {
        private const val ACTION_CHANCE = 1
        private const val INVISIBLE_DURATION = 3500
        private const val ATTACK_RESET_DELAY = 1000
        private const val INVISIBLE_DELAY = 10000
        private const val MAX_GHOST_ENEMY_SPAWNED = 5
        private const val GHOST_SPAWN_TIME_DELAY = 10000
        private const val LIGHTS_EVENT_DELAY = 20000
        private const val MAX_GHOSTS_ALIVE = 10
        private const val BOSS_ATTACK_VERTICAL_RANGE = 2
        private const val BOSS_ATTACK_HORIZONTAL_RANGE = 1
        private const val lock1 = 0
        private const val lock2 = 0
    }
}