package game.network.dispatch

import game.Bomberman
import game.network.models.HttpMessage
import game.network.models.HttpActor
import game.network.serializing.HttpParserSerializer
import game.utils.dev.Log

/**
 * A class responsible for dispatching HTTP messages within the Bomberman game.
 */
class HttpMessageDispatcher private constructor() {

    /**
     * Dispatches the given HTTP message to all clients.
     *
     * @param httpMessage The HTTP message to dispatch.
     */
    fun dispatch(httpMessage: HttpMessage) {
        dispatch(httpMessage, -1, false)
    }

    /**
     * Dispatches the given HTTP message to a specific client based on the receiverId.
     *
     * @param httpMessage The HTTP message to dispatch.
     * @param receiverId The ID of the intended receiver client.
     */
    fun dispatch(httpMessage: HttpMessage, receiverId: Long) {
        dispatch(httpMessage, receiverId, false)
    }

    /**
     * Dispatches the given HTTP message with options for targeted or broadcast delivery.
     *
     * @param httpMessage The HTTP message to dispatch.
     * @param receiverId The ID of the intended receiver client.
     * @param ignore If true, the message will be sent to all clients except the specified receiverId.
     */
    fun dispatch(httpMessage: HttpMessage, receiverId: Long, ignore: Boolean) {
        val data: String = HttpParserSerializer.instance.serialize(httpMessage)

        Log.i("HttpMessageDispatcher: $httpMessage, $receiverId, $ignore")

        for (sender in httpMessage.senders) {
            if (dispatch(data, sender, receiverId, ignore))
                break
        }
    }

    /**
     * Dispatches the serialized data to the specified HttpActor based on the Bomberman game context.
     *
     * @param data The serialized data to dispatch.
     * @param httpActor The target HttpActor (SERVER or CLIENT).
     * @param receiverId The ID of the intended receiver client.
     * @param ignore If true, the message will be sent to all clients except the specified receiverId.
     * @return True if the message was successfully dispatched, false otherwise.
     */
    private fun dispatch(data: String, httpActor: HttpActor, receiverId: Long, ignore: Boolean) : Boolean {
        Log.i("""
            {
              "message": "dispatch",
              "data": "$data",
              "actor": "$httpActor",
              "server": ${Bomberman.getMatch().isServer},
              "receiverId": ${receiverId},
              "ignore:" $ignore,"
              "client": ${Bomberman.getMatch().isClient}
            }
        """)

        if (httpActor == HttpActor.SERVER && Bomberman.getMatch().isServer) {
            Bomberman.getMatch().onlineGameHandler?.sendData(data, receiverId, ignore)
            return true
        } else if (httpActor == HttpActor.CLIENT && Bomberman.getMatch().isClient) {
            Bomberman.getMatch().onlineGameHandler?.sendData(data, receiverId, ignore)
            return true
        }

        return false
    }

    companion object {
        /**
         * Singleton instance of the HttpMessageDispatcher class.
         */
        val instance: HttpMessageDispatcher by lazy { HttpMessageDispatcher() }
    }
}
