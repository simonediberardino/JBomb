package game.network.messages

import game.domain.world.domain.entity.geo.Coordinates
import game.network.models.HttpActor
import game.network.models.HttpMessage
import game.network.models.HttpMessageTypes

class TimeHttpMessage(val time: Long) : HttpMessage {
    override fun serialize(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["messageType"] = HttpMessageTypes.TIME.ordinal.toString()
        data["time"] = time.toString()
        return data
    }

    override val senders: Array<HttpActor>
        get() = arrayOf(HttpActor.SERVER)
    }