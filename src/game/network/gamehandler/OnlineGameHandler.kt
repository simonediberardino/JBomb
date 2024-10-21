package game.network.gamehandler

interface OnlineGameHandler {
    suspend fun onStart()
    fun onClose()
    fun sendData(data: String)
    fun sendData(data: String, receiverId: Long, ignore: Boolean)
    fun onDataReceived(data: String)
    fun isRunning() : Boolean
    suspend fun disconnect()
}