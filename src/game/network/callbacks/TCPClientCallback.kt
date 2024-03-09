package game.network.callbacks

import game.network.gamehandler.OnlineGameHandler

interface TCPClientCallback : OnlineGameHandler {
    fun onError()
    fun onDisconnect()
    fun onConnect()
    fun onIdReceived(id: Long)
}