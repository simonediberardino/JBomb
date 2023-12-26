package game.http.callbacks

import game.http.sockets.TCPServer
import game.level.online.OnlineGameHandler

interface TCPServerCallback : OnlineGameHandler {
    fun onCloseServer()
    fun onStartServer()
    fun onClientConnected(indexedClient: TCPServer.IndexedClient)
}