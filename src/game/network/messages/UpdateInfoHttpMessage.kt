package game.network.messages

import game.network.entity.EntityNetwork
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes
import game.utils.dev.Extensions.toMap
import game.utils.dev.Log

class UpdateInfoHttpMessage(
        private val entityNetwork: EntityNetwork? = null,
        private val entityId: Long? = null,
        private val params: Map<String, String>? = null
) : HttpMessage {
    override fun serialize(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.UPDATE_INFO.ordinal.toString()

        entityNetwork?.toMap()?.let {
            data.putAll(it)
        }

        entityId?.let { data["entityId"] = it.toString() }
        params?.let { data.putAll(it) }

        Log.i("UpdateInfoHttpMessage $data")
        return data
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.CLIENT, HttpActor.SERVER)
}