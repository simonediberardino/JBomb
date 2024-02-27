package game.network.messages

import game.network.entity.EntityNetwork
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class AttackEntityHttpMessage(private val entity: EntityNetwork, private val damage: Int): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.ENTITY_ATTACKED.ordinal.toString()
        data["entityId"] = entity.entityId.toString()
        data["damage"] = damage.toString()
        return data.toString()
    }

    override val senders: Array<HttpActor> = arrayOf(HttpActor.SERVER)
}