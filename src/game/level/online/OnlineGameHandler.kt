package game.level.online

interface OnlineGameHandler {
    fun onStart()
    fun onClose()
    fun sendData(data: String)
    fun onDataReceived(data: String)
}