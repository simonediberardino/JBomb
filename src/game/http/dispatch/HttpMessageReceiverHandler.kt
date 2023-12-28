package game.http.dispatch

import game.actorbehavior.PlayerConnectedBehavior
import game.http.events.process.IdAssignedHttpEventProcessor
import game.http.events.process.PlayerConnectedHttpEventProcessor
import game.http.models.HttpMessageTypes

class HttpMessageReceiverHandler private constructor() {
    // Handles the behavior of each http message;
    fun handle(map: Map<String, String>) {
        val messageTypeInt = map["messageType"]?.toInt() ?: -1

        println("handling $map")
        // Temporary???
        when (HttpMessageTypes.values()[messageTypeInt]) {
            HttpMessageTypes.ASSIGN_ID -> {
                // After receiving an ID, the client requests to join the match;
                IdAssignedHttpEventProcessor().invoke(map)
            }
            HttpMessageTypes.PLAYER_JOIN_REQUEST -> {
                // After the host received a join request, spawns the connected player
                PlayerConnectedHttpEventProcessor().invoke(map)
            }
            else -> {}
        }
    }

    companion object {
        val instance: HttpMessageReceiverHandler by lazy { HttpMessageReceiverHandler() }
    }
}