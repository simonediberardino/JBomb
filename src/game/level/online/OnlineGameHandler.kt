package game.level.online

interface OnlineGameHandler {
    fun onStart()
    fun onClose()
    fun sendData(data: String)
    fun sendData(data: String, receiverId: Long)
    fun onDataReceived(data: String)
    fun isRunning() : Boolean
}