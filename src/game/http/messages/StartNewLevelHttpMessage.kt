package game.http.messages

import game.http.models.HttpActor
import game.http.models.HttpMessage
import game.http.models.HttpMessageTypes

class StartNewLevelHttpMessage(val levelId: Int, val worldId: Int): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.LOCATION.ordinal.toString()
        data["levelId"] = levelId.toString()
        data["worldId"] = worldId.toString()
        return data.toString()
    }

    override val senders: Array<HttpActor> = arrayOf(HttpActor.SERVER)
}