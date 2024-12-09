package game.domain.level.levels.multiplayer

import game.JBomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.presentation.ui.panels.game.PitchPanel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MultiplayerPowerupHandler() {
    private val spawnDelay = 60_000
    private val spawnCount = 2

    private fun getRandomPowerup(): Class<out PowerUp> {
        return JBomb.match.currentLevel.info.randomPowerUpClass
    }

    private fun choosePowerupPosition(): Coordinates {
        return Coordinates.generateRandomCoordinates(BomberEntity.SPAWN_OFFSET, PitchPanel.GRID_SIZE)
    }

    private fun spawnPowerup() {
        getRandomPowerup().getConstructor(Coordinates::class.java).newInstance(choosePowerupPosition()).logic.spawn()
    }

    fun spawnLoop() {
        if (!JBomb.match.isServer)
            return

        JBomb.match.scope.launch {
            while (true) {
                repeat(spawnCount) {
                    spawnPowerup()
                }

                delay(spawnDelay.toLong())
            }
        }
    }
}