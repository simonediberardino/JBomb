package game.network.messages

import game.network.entity.EntityNetwork
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class EntityKilledByHttpMessage(
    val victim: EntityNetwork,
    val attacker: EntityNetwork
): HttpMessage {
    override fun serialize(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.KILLED_BY.ordinal.toString()
        data["victimId"] = victim.entityId.toString()
        data["attackerId"] = attacker.entityId.toString()
        return data
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
}