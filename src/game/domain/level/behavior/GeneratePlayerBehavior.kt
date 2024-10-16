package game.domain.level.behavior

import game.JBomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.domain.entity.geo.Coordinates
import game.properties.RuntimeProperties

class GeneratePlayerBehavior(val coordinates: Coordinates) : GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            if (!RuntimeProperties.dedicatedServer) {
                JBomb.match.player = Player(coordinates)
                JBomb.match.player!!.logic.spawn(forceSpawn = false, forceCentering = false)
            }
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}