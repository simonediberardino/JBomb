package game.http.callbacks

import game.http.sockets.TCPServer

interface TCPServerCallback {
    fun onCloseServer()
    fun onStartServer()
    fun onClientConnected(indexedClient: TCPServer.IndexedClient)
}