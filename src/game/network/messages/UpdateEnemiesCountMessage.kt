package game.network.messages

import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class UpdateEnemiesCountMessage(private val count: Int): HttpMessage {
    override fun serialize(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.ENEMIES_COUNT.ordinal.toString()
        data["enemiesCount"] = count.toString()
        return data
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
}