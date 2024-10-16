package game.network.usecases

import game.internet.JBombHttp
import game.moshi.serversListAdapter
import game.presentation.ui.pages.server_browser.ServerInfo
import game.usecases.UseCase
import game.values.HttpUrls

class FetchAllServersFromMasterServer: UseCase<List<ServerInfo>?> {
    override suspend fun invoke(): List<ServerInfo>? {
        println("Fetching")
        val response = JBombHttp.get("${HttpUrls.masterServerUrl}/servers")
        return if (response.statusCode == 200) {
            serversListAdapter.fromJson(response.data!!)
        } else null
    }
}