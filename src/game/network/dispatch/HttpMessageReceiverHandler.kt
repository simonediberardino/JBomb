package game.network.dispatch

import game.network.events.process.*
import game.network.models.HttpMessageTypes
import game.utils.Extensions.getOrTrim
import game.utils.Log

class HttpMessageReceiverHandler private constructor() {
    // Handles the behavior of each http message;
    fun handle(map: Map<String, String>) {
        val messageTypeInt = map.getOrTrim("messageType")?.toInt() ?: -1

        Log.i("handling $map")

        val httpEvent = when (HttpMessageTypes.values()[messageTypeInt]) {
            HttpMessageTypes.PLAYER_JOIN_REQUEST -> PlayerConnectedHttpEventProcessor()
            HttpMessageTypes.LEVEL_INFO -> LevelInfoHttpEventProcessor()
            HttpMessageTypes.SPAWNED_ENTITY -> SpawnedEntityHttpEventProcessor()
            HttpMessageTypes.DESPAWNED_ENTITY -> DespawnedEntityHttpEventProcessor()
            HttpMessageTypes.ENTITY_ATTACKED -> AttackEntityEventProcessor()
            HttpMessageTypes.LOCATION -> LocationUpdatedHttpEventProcessor()
            HttpMessageTypes.USE_ITEM -> UseItemHttpEventProcessor()
            else -> null
        }

        httpEvent?.invoke(map)
    }

    companion object {
        val instance: HttpMessageReceiverHandler by lazy { HttpMessageReceiverHandler() }
    }
}