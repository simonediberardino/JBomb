package game.network.callbacks

import game.engine.level.online.OnlineGameHandler

interface TCPClientCallback : OnlineGameHandler {
    fun onError()
    fun onDisconnect()
    fun onConnect()
    fun onIdReceived(id: Long)
}