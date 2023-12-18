package game.http.dispatch

import game.http.models.HttpMessage
import game.http.models.HttpActor
import game.http.serializing.HttpParserSerializer

class HttpMessageDispatcher private constructor() {
    fun dispatch(httpMessage: HttpMessage) {
        val data: String = HttpParserSerializer.instance.serialize(httpMessage)

        for (receiver in httpMessage.senders) {
            dispatch(data, receiver)
        }
    }

    fun dispatch(data: String, httpActor: HttpActor) {

    }
    companion object {
        val instance: HttpMessageDispatcher by lazy { HttpMessageDispatcher() }
    }
}