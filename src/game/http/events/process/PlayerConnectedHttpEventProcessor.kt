package game.http.events.process

import game.Bomberman
import game.entity.player.RemotePlayer
import game.events.models.HttpEvent
import game.utils.Extensions.getOrTrim

class PlayerConnectedHttpEventProcessor : HttpEvent {
    override fun invoke(info: Map<String, String>) {
        val clientId = info.getOrTrim("id")?.toLong()
        println("PlayerConnectedHttpEventProcessor: $clientId")

        val match = Bomberman.getMatch()
        val coordinates = (match.currentLevel)?.info?.playerSpawnCoordinates

        if (coordinates != null && clientId != null) {
            val player = RemotePlayer(coordinates, clientId, 1)
            player.spawn()
        }
    }
}