package game.http.models

import game.http.sockets.TCPClient
import game.http.sockets.TCPSocket
import kotlin.reflect.KFunction2

enum class HttpActor {
    CLIENT,
    SERVER
}