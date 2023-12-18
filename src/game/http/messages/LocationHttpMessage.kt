package game.http.messages

import game.http.dao.CharacterDao
import game.http.dao.EntityDao
import game.http.models.HttpActor
import game.http.models.HttpMessage
import game.http.models.HttpMessageTypes

class LocationHttpMessage(private val character: CharacterDao): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.LOCATION.ordinal.toString()
        data["entityId"] = character.entityId.toString()
        data["location"] = "${character.entityLocation.x} ${character.entityLocation.y}"
        data["direction"] = character.direction.toString()
        data["skinStatus"] = character.skinStatus.toString()
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() {
            return arrayOf(HttpActor.CLIENT, HttpActor.SERVER)
        }
}