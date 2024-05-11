package game.network.messages

import game.network.entity.CharacterNetwork
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class LocationHttpMessage(private val character: CharacterNetwork, private val sentByClient: Boolean = false): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.LOCATION.ordinal.toString()
        data["entityId"] = character.entityId.toString()
        data["location"] = "${character.entityLocation!!.x} ${character.entityLocation.y}"
        data["direction"] = character.direction.toString()
        data["sentByClient"] = sentByClient.toString()

        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.CLIENT, HttpActor.SERVER)
}