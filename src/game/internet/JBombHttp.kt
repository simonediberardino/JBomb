package game.internet

import game.utils.dev.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object JBombHttp {
    suspend fun <T> get(url: String): JBombHttpResponse {
        return suspendCoroutine { continuation ->
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode

                val content = StringBuilder()
                BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        content.append(line).append('\n')
                    }
                }

                Log.e("Response for $url: $content with $responseCode")
                connection.disconnect()

                continuation.resume(JBombHttpResponse(statusCode = responseCode, data = content.toString()))
            } catch (exception: Exception) {
                continuation.resume(JBombHttpResponse(-1, null))
            }
        }
    }
}

class JBombHttpResponse(
        val statusCode: Int,
        val data: String?
)