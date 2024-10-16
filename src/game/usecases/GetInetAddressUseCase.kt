package game.usecases

import game.internet.JBombHttp
import game.values.HttpUrls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress

class GetInetAddressUseCase: UseCase<InetAddress?> {
    override suspend fun invoke(): InetAddress? {
        val result = JBombHttp.get(HttpUrls.getInetAddressUrl)

        return if (result.statusCode == 200) {
            withContext(Dispatchers.IO) {
                InetAddress.getByName(result.data?.trim())
            }
        } else {
            return null
        }
    }
}