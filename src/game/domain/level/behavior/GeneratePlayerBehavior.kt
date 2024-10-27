package game.domain.level.behavior

import game.JBomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.domain.entity.geo.Coordinates
import game.properties.RuntimeProperties

class GeneratePlayerBehavior : GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            if (!RuntimeProperties.dedicatedServer) {
                val level = JBomb.match.currentLevel

                if (level.info.customSpawnpoints.isNotEmpty()) {
                    JBomb.match.player = Player(Coordinates()).also {
                        level.gameHandler.dynamicSpawn(it)
                    }
                } else {
                    JBomb.match.player = Player(level.info.playerSpawnCoordinates).also {
                        it.logic.spawn(forceSpawn = false, forceCentering = false)
                    }
                }
            }
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}