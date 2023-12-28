package game.http.dispatch

import game.Bomberman
import game.http.models.HttpMessage
import game.http.models.HttpActor
import game.http.serializing.HttpParserSerializer

class HttpMessageDispatcher private constructor() {
    fun dispatch(httpMessage: HttpMessage) {
        dispatch(httpMessage, -1)
    }

    fun dispatch(httpMessage: HttpMessage, receiverId: Int) {
        val data: String = HttpParserSerializer.instance.serialize(httpMessage)

        println("HttpMessageDispatcher: $httpMessage, $receiverId")

        for (sender in httpMessage.senders) {
            if (dispatch(data, sender, receiverId))
                break
        }
    }

    private fun dispatch(data: String, httpActor: HttpActor, receiverId: Int) : Boolean {
        println("""
            {
              "message": "dispatch",
              "data": "$data",
              "actor": "$httpActor",
              "server": ${Bomberman.getMatch().isServer},
              "receiverId": ${receiverId},
              "client": ${Bomberman.getMatch().isClient}
            }
        """)

        if (httpActor == HttpActor.SERVER && Bomberman.getMatch().isServer) {
            Bomberman.getMatch().onlineGameHandler?.sendData(data, receiverId)
            return true
        } else if (httpActor == HttpActor.CLIENT && Bomberman.getMatch().isClient) {
            Bomberman.getMatch().onlineGameHandler?.sendData(data, receiverId)
            return true
        }

        return false
    }

    companion object {
        val instance: HttpMessageDispatcher by lazy { HttpMessageDispatcher() }
    }
}