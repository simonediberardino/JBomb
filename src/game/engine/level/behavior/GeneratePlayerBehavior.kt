package game.engine.level.behavior

import game.Bomberman
import game.engine.world.entity.impl.player.Player
import game.engine.world.geo.Coordinates

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