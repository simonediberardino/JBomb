package game.http.messages

import game.http.dao.EntityDao
import game.http.models.HttpActor
import game.http.models.HttpMessage
import game.http.models.HttpMessageTypes

class LocationHttpMessage(val entity: EntityDao): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.LOCATION.ordinal.toString()
        data["entityId"] = entity.entityId.toString()
        data["location"] = "${entity.entityLocation.x} ${entity.entityLocation.y}"
        return data.toString()
    }

    override val receivers: Array<HttpActor>
        get() {
            return arrayOf(HttpActor.CLIENT, HttpActor.SERVER)
        }
}