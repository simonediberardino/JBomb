package game.http.messages

import game.http.dao.EntityDao
import game.http.models.HttpActor
import game.http.models.HttpMessage
import game.http.models.HttpMessageTypes

class LocationUpdatedHttpMessage(private val entity: EntityDao): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.LOCATION.ordinal.toString()
        data["entityId"] = entity.entityId.toString()
        data["entityType"] = entity.entityType.toString()
        data["location"] = "${entity.entityLocation.x} ${entity.entityLocation.y}"
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
}