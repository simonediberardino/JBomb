package game.network.messages

import game.engine.world.entity.dto.CharacterDto
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class LocationHttpMessage(private val character: CharacterDto): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.LOCATION.ordinal.toString()
        data["entityId"] = character.entityId.toString()
        data["location"] = "${character.entityLocation!!.x} ${character.entityLocation.y}"
        data["direction"] = character.direction.toString()
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.CLIENT, HttpActor.SERVER)
}