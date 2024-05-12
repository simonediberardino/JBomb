package game.network.messages

import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class PlayerJoinRequestHttpMessage(private val id: Int, private val extra: Any): HttpMessage {
    override fun serialize(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.PLAYER_JOIN_REQUEST.ordinal.toString()
        data["id"] = id.toString()
        data["extra"] = extra.toString()
        return data
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.CLIENT)
}