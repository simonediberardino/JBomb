package game.network.messages

import game.engine.world.network.dto.EntityDto
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class DespawnedEntityHttpMessage(private val entity: EntityDto): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.DESPAWNED_ENTITY.ordinal.toString()
        data["entityId"] = entity.entityId.toString()
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER, HttpActor.CLIENT)
}