package game.http.dispatch

import game.actorbehavior.PlayerConnectedBehavior
import game.events.http.IdAssignedHttpEvent
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
                IdAssignedHttpEvent().invoke(map)
            }
            HttpMessageTypes.PLAYER_JOIN_REQUEST -> {
                // After the host received a join request, spawns the connected player
                PlayerConnectedBehavior(map).invoke()
            }
            else -> {}
        }
    }

    companion object {
        val instance: HttpMessageReceiverHandler by lazy { HttpMessageReceiverHandler() }
    }
}