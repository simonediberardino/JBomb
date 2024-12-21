package game.network.events.process

import game.JBomb
import game.domain.events.models.HttpEvent
import game.domain.world.domain.entity.actors.impl.bomber_entity.remote_player.RemotePlayer
import game.domain.world.domain.entity.geo.Coordinates
import game.network.events.forward.SpawnEntityEventForwarder
import game.network.gamehandler.ServerGameHandler
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class PlayerConnectedHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        val clientId = info.getOrTrim("id")?.toLong() ?: return

        Log.i("PlayerConnectedHttpEventProcessor: $clientId")

        val match = JBomb.match
        val level = match.currentLevel

        match.resumeIfPaused()

        match.getEntities().forEach { e ->
            SpawnEntityEventForwarder(clientId).invoke(e.toEntityNetwork())
        }

        val skinId = info.getOrTrim("skinId")
        val player = RemotePlayer(null, clientId, skinId?.toInt() ?: 0)

        if (level.info.customSpawnpoints.isNotEmpty()) {
            player.info.position = level.gameHandler.chooseSpawnpointLogic(player)
        } else {
            level.info.playerSpawnCoordinates
        }

        player.updateInfo(info)
        player.logic.spawn(forceSpawn = true, forceCentering = true)

        Log.i("[PlayerConnectedHttpEventProcessor] Game state = ${JBomb.match.gameState}")
        Log.i("[PlayerConnectedHttpEventProcessor] Game ticker = ${JBomb.match.gameTickerObservable}")
        Log.i("[PlayerConnectedHttpEventProcessor] Game ticker state = ${JBomb.match.gameTickerObservable?.isRunning}")

        level.playerCountHandler.onPlayerCountChanged()

        // TODO Find a better solution
        (JBomb.match.onlineGameHandler as? ServerGameHandler?)?.onClientJoinedSuccessfully(clientId)
    }
}