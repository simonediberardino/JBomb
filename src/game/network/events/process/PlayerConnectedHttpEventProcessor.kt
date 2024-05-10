package game.network.events.process

import game.Bomberman
import game.domain.events.models.HttpEvent
import game.domain.world.domain.entity.actors.impl.bomber_entity.remote_player.RemotePlayer
import game.network.events.forward.SpawnEntityEventForwarder
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class PlayerConnectedHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        val clientId = info.getOrTrim("id")?.toLong() ?: return
        Log.i("PlayerConnectedHttpEventProcessor: $clientId")

        val match = Bomberman.match
        val coordinates = match.currentLevel.info.playerSpawnCoordinates

        Bomberman.match.resumeIfPaused()

        match.getEntities().forEach { e ->
            Log.i("Sending entity $e to $clientId")
            SpawnEntityEventForwarder(clientId).invoke(e.toEntityNetwork())
        }

        val player = RemotePlayer(coordinates, clientId, 1)
        player.logic.spawn()
    }
}