package game.network.messages

import game.engine.world.entity.dto.EntityDto
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class SpawnedEntityHttpMessage(private val entity: EntityDto, private val extras: Map<String, String> = hashMapOf()): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.SPAWNED_ENTITY.ordinal.toString()
        data["entityId"] = entity.entityId.toString()
        data["entityType"] = entity.entityType.toString()
        data["location"] = "${entity.entityLocation!!.x} ${entity.entityLocation.y}"
        data.putAll(extras)
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
}