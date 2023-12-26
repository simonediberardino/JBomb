package game.http.dispatch

import game.Bomberman
import game.http.models.HttpMessage
import game.http.models.HttpActor
import game.http.serializing.HttpParserSerializer

class HttpMessageDispatcher private constructor() {
    fun dispatch(httpMessage: HttpMessage) {
        val data: String = HttpParserSerializer.instance.serialize(httpMessage)

        println("HttpMessageDispatcher: $httpMessage")
        for (sender in httpMessage.senders) {
            if (dispatch(data, sender))
                break
        }
    }

    private fun dispatch(data: String, httpActor: HttpActor) : Boolean {
        println("dispatch $data, actor $httpActor, server: ${ Bomberman.getMatch().isServer}, client: ${ Bomberman.getMatch().isClient}")
        if (httpActor == HttpActor.SERVER && Bomberman.getMatch().isServer) {
            Bomberman.getMatch().onlineGameHandler?.sendData(data)
            return true
        } else if (httpActor == HttpActor.CLIENT && Bomberman.getMatch().isClient) {
            Bomberman.getMatch().onlineGameHandler?.sendData(data)
            return true
        }

        return false
    }

    companion object {
        val instance: HttpMessageDispatcher by lazy { HttpMessageDispatcher() }
    }
}