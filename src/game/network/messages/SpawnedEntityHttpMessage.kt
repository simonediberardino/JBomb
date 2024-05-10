package game.network.messages

import game.network.entity.EntityNetwork
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes
import game.utils.dev.Extensions.toMap

class SpawnedEntityHttpMessage(private val entity: EntityNetwork, private val extras: Map<String, String> = hashMapOf()): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.SPAWNED_ENTITY.ordinal.toString()
        data["entityId"] = entity.entityId.toString()
        data["entityType"] = entity.entityType.toString()
        data["location"] = "${entity.entityLocation!!.x} ${entity.entityLocation.y}"
        data.putAll(extras)
        data.putAll(entity.toMap())
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
}