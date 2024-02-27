package game.network.messages

import game.network.entity.EntityNetwork
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes
import game.engine.world.domain.entity.items.ItemsTypes

class UseItemHttpMessage(private val userDao: EntityNetwork, private val itemType: ItemsTypes) : HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.USE_ITEM.ordinal.toString()
        data["entityId"] = userDao.entityId.toString()
        data["itemType"] = itemType.toInt().toString()
        return data.toString()
    }

    override val senders: Array<HttpActor> = arrayOf(HttpActor.SERVER, HttpActor.CLIENT)
}