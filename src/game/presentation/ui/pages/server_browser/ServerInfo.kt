package game.presentation.ui.pages.server_browser

// ServerInfo class to hold server details
class ServerInfo(val name: String, val ip: String, val port: Int, val players: Int, val ping: Int) {

    val fullIp: String
        get() = "$ip:$port"
}
