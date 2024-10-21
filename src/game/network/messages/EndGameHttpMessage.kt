package game.network.messages

import game.network.entity.EntityNetwork
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class EndGameHttpMessage: HttpMessage {
    override fun serialize(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.GAME_ENDED.ordinal.toString()
        return data
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
}