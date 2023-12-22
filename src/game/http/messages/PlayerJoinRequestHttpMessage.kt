package game.http.messages

import game.http.models.HttpActor
import game.http.models.HttpMessage
import game.http.models.HttpMessageTypes

class PlayerJoinRequestHttpMessage(private val id: Int): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.PLAYER_JOIN_REQUEST.ordinal.toString()
        data["id"] = id.toString()
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.CLIENT)
}