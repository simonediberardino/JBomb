package game.http.callbacks

interface TCPClientCallback {
    fun onDisconnect()
    fun onConnect()
    fun onDataReceived(data: String)
    fun onIdReceived(id: Int)
}