package game.engine.level.behavior

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.player.Player
import game.engine.world.domain.entity.geo.Coordinates

class GeneratePlayerBehavior(val coordinates: Coordinates): GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            Bomberman.getMatch().player = Player(coordinates)
            Bomberman.getMatch().player?.spawn(false, false)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}