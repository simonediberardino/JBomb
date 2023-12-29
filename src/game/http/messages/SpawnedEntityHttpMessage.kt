package game.http.messages

import game.http.dao.CharacterDao
import game.http.dao.EntityDao
import game.http.models.HttpActor
import game.http.models.HttpMessage
import game.http.models.HttpMessageTypes

class SpawnedEntityHttpMessage(private val entity: EntityDao): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.SPAWNED_ENTITY.ordinal.toString()
        data["entityId"] = entity.entityId.toString()
        data["entityType"] = entity.entityType.toString()
        data["location"] = "${entity.entityLocation.x} ${entity.entityLocation.y}"
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
}