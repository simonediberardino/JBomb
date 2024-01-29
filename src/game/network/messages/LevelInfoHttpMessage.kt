package game.network.messages

import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class LevelInfoHttpMessage(private val id: Long, private val levelId: Int, private val worldId: Int): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.LEVEL_INFO.ordinal.toString()
        data["id"] = id.toString()
        data["levelId"] = levelId.toString()
        data["worldId"] = worldId.toString()
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
}