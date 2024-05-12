package game.network.messages

import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class AssignIdHttpMessage(private val id: Int): HttpMessage {
    override fun serialize(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.ASSIGN_ID.ordinal.toString()
        data["id"] = id.toString()
        return data
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
}