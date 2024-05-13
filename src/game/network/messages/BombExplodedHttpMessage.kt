package game.network.messages

import game.network.entity.EntityNetwork
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes
import game.utils.dev.Log

class BombExplodedHttpMessage(
        private val caller: EntityNetwork,
        private val bomb: EntityNetwork
): HttpMessage {
    override fun serialize(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.BOMB_EXPLODED.ordinal.toString()
        data["entityId"] = caller.entityId.toString()
        data["bombId"] = bomb.entityId.toString()

        Log.e("BombExplodedHttpMessage Sending $data")
        return data
    }

    override val senders: Array<HttpActor> = arrayOf(HttpActor.SERVER, HttpActor.CLIENT)
}