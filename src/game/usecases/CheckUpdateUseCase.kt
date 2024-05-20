package game.usecases

import game.internet.JBombHttp
import game.utils.dev.XMLUtils
import game.utils.file_system.Paths.versionXml
import game.values.HttpUrls
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.cancellation.CancellationException

class CheckUpdateUseCase(val timeout: Long = 15_000L) : UseCase<Boolean> {
    override suspend fun invoke(): Boolean {
        return try {
            withTimeout(timeout) {
                val currVersion = XMLUtils.readXmlKey(versionXml, "version").toInt()
                val result = JBombHttp.get<String>(HttpUrls.JBombVersionUrl)

                val serverVersion = result.data?.trim()?.toInt() ?: 0
                result.statusCode == 200 && (currVersion < serverVersion)
            }
        } catch (cancelled: CancellationException) {
            false
        }
    }
}