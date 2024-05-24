package game.usecases

import game.internet.JBombHttp
import game.values.HttpUrls
import java.net.InetAddress
import javax.jws.soap.SOAPBinding.Use

class GetInetAddressUseCase: UseCase<InetAddress?> {
    override suspend fun invoke(): InetAddress? {
        val result = JBombHttp.get<String>(HttpUrls.getInetAddressUrl)

        return if (result.statusCode == 200) {
            InetAddress.getByName(result.data?.trim())
        } else {
            return null
        }
    }
}