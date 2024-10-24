package game.network.usecases

import game.internet.JBombHttp
import game.moshi.serversListAdapter
import game.presentation.ui.pages.server_browser.ServerInfo
import game.usecases.GetInetAddressUseCase
import game.usecases.UseCase
import game.utils.dev.Log
import game.values.HttpUrls
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class FetchAllServersFromMasterServerUseCase : UseCase<List<ServerInfo>?> {
    override suspend fun invoke(): List<ServerInfo>? {
        val myIpv4 = GetInetAddressUseCase().invoke()?.hostName

        val response = JBombHttp.get("${HttpUrls.masterServerUrl}/servers")
        return if (response.statusCode == 200) {
            val servers = serversListAdapter.fromJson(response.data!!)
            Log.i("Available servers $servers")

            // Map each server to an asynchronous ping task
            coroutineScope {
                // Map each server to an asynchronous ping task
                servers?.map { server ->
                    async {
                        // Execute the ping and pair the result with the server
                        val isReachable = PingServerUseCase(server.ip, server.port).invoke()
                        Pair(server, isReachable)
                    }
                }?.awaitAll() // Wait for all ping tasks to complete
                    ?.filter { it.second } // Filter by reachable servers (those with true in the pair)
                    ?.map { it.first } // Return only the server objects
            }

        } else null
    }
}