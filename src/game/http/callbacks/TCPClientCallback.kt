package game.http.callbacks

import game.level.online.OnlineGameHandler

interface TCPClientCallback : OnlineGameHandler {
    fun onError()
    fun onDisconnect()
    fun onConnect()
    fun onIdReceived(id: Long)
}