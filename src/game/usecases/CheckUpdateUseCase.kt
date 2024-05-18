package game.usecases

import game.internet.JBombHttp
import game.utils.dev.XMLUtils
import game.utils.file_system.Paths.versionXml
import game.values.HttpUrls

class CheckUpdateUseCase : UseCase<Boolean> {
    override suspend fun invoke(): Boolean {
        val currVersion = XMLUtils.readXmlKey(versionXml, "version").toInt()
        val result = JBombHttp.get<String>(HttpUrls.JBombVersionUrl)

        val serverVersion = result.data?.trim()?.toInt() ?: 0
        return result.statusCode == 200 && (currVersion < serverVersion)
    }
}