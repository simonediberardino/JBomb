package game.domain.level.behavior

import game.Bomberman
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.domain.entity.geo.Coordinates
import game.utils.dev.Log

class GeneratePlayerBehavior(val coordinates: Coordinates): GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            Bomberman.match.player = Player(coordinates)
            Bomberman.match.player!!.logic.spawn(forceSpawn = false, forceCentering = false)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}