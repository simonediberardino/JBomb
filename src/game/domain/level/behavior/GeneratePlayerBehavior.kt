package game.domain.level.behavior

import game.Bomberman
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.domain.entity.geo.Coordinates

class GeneratePlayerBehavior(val coordinates: Coordinates): GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            Bomberman.getMatch().player = Player(coordinates)
            Bomberman.getMatch().player!!.logic.spawn(forceSpawn = false, forceCentering = false)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}