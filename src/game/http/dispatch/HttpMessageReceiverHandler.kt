package game.http.dispatch

import game.Bomberman
import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.events.http.CharacterSpawnedHttpEvent
import game.events.http.IdAssignedHttpEvent
import game.http.dao.CharacterDao
import game.http.messages.LocationHttpMessage
import game.http.models.HttpActor
import game.http.models.HttpMessageTypes

class HttpMessageReceiverHandler private constructor() {
    fun handle(map: Map<String, String>) {
        val messageTypeInt = map["messageType"]?.toInt() ?: -1

        // Temporary???
        when (HttpMessageTypes.values()[messageTypeInt]) {
            HttpMessageTypes.ASSIGN_ID -> {
                IdAssignedHttpEvent().invoke(map)
            }
            HttpMessageTypes.PLAYER_JOIN_REQUEST -> {
                CharacterSpawnedHttpEvent().invoke(map)
            }
            else -> {}
        }
    }

    companion object {
        val instance: HttpMessageReceiverHandler by lazy { HttpMessageReceiverHandler() }
    }
}