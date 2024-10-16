package game.network.callbacks

import game.network.sockets.TCPServer
import game.network.gamehandler.OnlineGameHandler

interface TCPServerCallback : OnlineGameHandler {
    suspend fun onCloseServer()
    fun onStartServer()
    fun onClientConnected(indexedClient: TCPServer.IndexedClient)
    fun onClientDisconnected(indexedClient: TCPServer.IndexedClient)
}