package game.network.messages

import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class StartNewLevelHttpMessage(val levelId: Int, val worldId: Int): HttpMessage {
    override fun serialize(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.LOCATION.ordinal.toString()
        data["levelId"] = levelId.toString()
        data["worldId"] = worldId.toString()
        return data
    }

    override val senders: Array<HttpActor> = arrayOf(HttpActor.SERVER)
}