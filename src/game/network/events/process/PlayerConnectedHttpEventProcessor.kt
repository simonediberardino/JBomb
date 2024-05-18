package game.network.events.process

import game.JBomb
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

        val match = JBomb.match
        val coordinates = match.currentLevel.info.playerSpawnCoordinates

        JBomb.match.resumeIfPaused()

        match.getEntities().forEach { e ->
            Log.i("Sending entity $e to $clientId")
            SpawnEntityEventForwarder(clientId).invoke(e.toEntityNetwork())
        }

        val skinId = info.getOrTrim("skinId")
        val player = RemotePlayer(coordinates, clientId, skinId?.toInt() ?: 0)
        player.updateInfo(info)
        player.logic.spawn()
    }
}