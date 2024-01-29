package game.network.messages

import game.engine.world.entity.dto.EntityDto
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes
import game.utils.Extensions.toMap
import game.utils.Log

class UpdateInfoHttpMessage(private val entityDto: EntityDto) : HttpMessage {
    override fun serialize(): String {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.UPDATE_INFO.ordinal.toString()
        data.putAll(entityDto.toMap())

        Log.i("UpdateInfoHttpMessage $data")
        return data.toString()
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.CLIENT, HttpActor.SERVER)
}