package game.network.usecases

import game.internet.JBombHttp
import game.usecases.UseCase
import game.utils.dev.Log
import game.values.HttpUrls
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SendStopServerToMasterServerUseCase(private val ip: String, private val port: Int): UseCase<Unit> {
    override suspend fun invoke() {
        Log.e("Sending delete")
        val map = mapOf(
            "ip" to ip,
            "port" to port.toString()
        )

        val response = JBombHttp.delete("${HttpUrls.masterServerUrl}/server", Json.encodeToString(map))

        Log.e("Data ${response.data}")
    }
}