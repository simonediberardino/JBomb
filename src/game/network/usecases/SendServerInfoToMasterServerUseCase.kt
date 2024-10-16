package game.network.usecases

import game.internet.JBombHttp
import game.presentation.ui.pages.server_browser.ServerInfo
import game.usecases.UseCase
import game.utils.dev.Extensions.toMap
import game.values.HttpUrls.masterServerUrl
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SendServerInfoToMasterServerUseCase(private val serverInfo: ServerInfo): UseCase<Unit> {
    override suspend fun invoke() {
        val payload = Json.encodeToString(serverInfo.toMap())
        JBombHttp.post("$masterServerUrl/server", payload)
    }
}