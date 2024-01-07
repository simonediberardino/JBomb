package game.http.messages

import game.http.dao.EntityDao
import game.http.models.HttpActor
import game.http.models.HttpMessage
import game.http.models.HttpMessageTypes
import game.items.ItemsTypes

class UseItemHttpMessage(private val userDao: EntityDao, private val itemType: ItemsTypes) : HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.USE_ITEM.ordinal.toString()
        data["entityId"] = userDao.entityId.toString()
        data["itemType"] = itemType.toInt().toString()
        return data.toString()
    }

    override val senders: Array<HttpActor> = arrayOf(HttpActor.SERVER, HttpActor.CLIENT)
}