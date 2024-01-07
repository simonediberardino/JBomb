package game.http.messages

import game.http.dao.EntityDao
import game.http.models.HttpActor
import game.http.models.HttpMessage
import game.http.models.HttpMessageTypes

class AttackEntityHttpMessage(private val entity: EntityDao, private val damage: Int): HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.ENTITY_ATTACKED.ordinal.toString()
        data["entityId"] = entity.entityId.toString()
        data["damage"] = damage.toString()
        return data.toString()
    }

    override val senders: Array<HttpActor> = arrayOf(HttpActor.SERVER)
}