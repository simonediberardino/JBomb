package game.network.gamehandler

interface OnlineGameHandler {
    fun onStart()
    fun onClose()
    fun sendData(data: String)
    fun sendData(data: String, receiverId: Long, ignore: Boolean)
    fun onDataReceived(data: String)
    fun isRunning() : Boolean
    fun disconnect()
}