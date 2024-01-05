package game.http.events.process

import game.Bomberman
import game.entity.player.RemotePlayer
import game.events.models.HttpEvent
import game.http.events.forward.SpawnEntityEventForwarder
import game.utils.Extensions.getOrTrim

class PlayerConnectedHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        val clientId = info.getOrTrim("id")?.toLong() ?: return
        println("PlayerConnectedHttpEventProcessor: $clientId")

        val match = Bomberman.getMatch()
        val coordinates = (match.currentLevel ?: return).info.playerSpawnCoordinates

        match.getEntities().forEach { e ->
            println("Sending entity $e to $clientId")
            SpawnEntityEventForwarder(clientId).invoke(e.toDao(), e.extras)
        }

        val player = RemotePlayer(coordinates, clientId, 1)
        player.spawn()
    }
}