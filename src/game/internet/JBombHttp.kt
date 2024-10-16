package game.internet

import game.presentation.ui.pages.server_browser.ServerInfo
import game.utils.dev.Extensions.toMap
import game.utils.dev.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object JBombHttp {

    // Generalized method to handle HTTP requests
    private suspend fun makeHttpRequest(url: String, method: String, payload: String? = null): JBombHttpResponse {
        return suspendCoroutine { continuation ->
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = method

                // If method is POST or DELETE and payload is provided, write to output stream
                if ((method == "POST" || method == "DELETE") && payload != null) {
                    connection.doOutput = true
                    connection.setRequestProperty("Content-Type", "application/json")
                    DataOutputStream(connection.outputStream).use { outputStream ->
                        outputStream.writeBytes(payload)
                        outputStream.flush()
                    }
                }

                // Get response code and content
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

                // Resume coroutine with the response
                continuation.resume(JBombHttpResponse(statusCode = responseCode, data = content.toString()))
            } catch (exception: Exception) {
                continuation.resume(JBombHttpResponse(-1, null))
            }
        }
    }

    // GET method implementation
    suspend fun get(url: String): JBombHttpResponse {
        return makeHttpRequest(url, "GET")
    }

    // POST method implementation
    suspend fun post(url: String, payload: String): JBombHttpResponse {
        return makeHttpRequest(url, "POST", payload)
    }

    // DELETE method implementation
    suspend fun delete(url: String, payload: String? = null): JBombHttpResponse {
        return makeHttpRequest(url, "DELETE", payload)
    }
}

// Class to hold the HTTP response
class JBombHttpResponse(
    val statusCode: Int,
    val data: String?
)
