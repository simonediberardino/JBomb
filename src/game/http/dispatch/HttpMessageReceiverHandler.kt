package game.http.dispatch

import game.http.events.process.LevelInfoHttpEventProcessor
import game.http.events.process.PlayerConnectedHttpEventProcessor
import game.http.events.process.SpawnedEntityHttpEventProcessor
import game.http.models.HttpMessageTypes

class HttpMessageReceiverHandler private constructor() {
    // Handles the behavior of each http message;
    fun handle(map: Map<String, String>) {
        val messageTypeInt = map["messageType"]?.toInt() ?: -1

        println("handling $map")
        // Temporary???
        when (HttpMessageTypes.values()[messageTypeInt]) {
            HttpMessageTypes.PLAYER_JOIN_REQUEST -> {
                // After the host received a join request, spawns the connected player
                PlayerConnectedHttpEventProcessor().invoke(map)
            }
            HttpMessageTypes.LEVEL_INFO -> {
                LevelInfoHttpEventProcessor().invoke(map)
            }
            HttpMessageTypes.SPAWNED_ENTITY -> {
                SpawnedEntityHttpEventProcessor().invoke(map)
            }
            else -> {}
        }
    }

    companion object {
        val instance: HttpMessageReceiverHandler by lazy { HttpMessageReceiverHandler() }
    }
}