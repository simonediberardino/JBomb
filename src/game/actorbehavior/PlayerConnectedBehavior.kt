package game.actorbehavior

import game.Bomberman
import game.entity.Player
import game.level.online.ClientGameHandler
import game.utils.Extensions.getOrTrim

class PlayerConnectedBehavior(val info: Map<String, String>) : GameBehavior {
    override fun hostBehavior(): () -> Unit {
        return {
            val clientId = info.getOrTrim("id")?.toLong()
            println("PlayerConnectedBehavior: $clientId")

            val match = Bomberman.getMatch()
            val coordinates = (match.currentLevel)?.info?.playerSpawnCoordinates

            if (coordinates != null && clientId != null) {
                val player = Player(coordinates, clientId)
                player.spawn()
            }
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}