package game.http.serializing

import com.google.common.base.Splitter
import game.http.models.HttpMessage

class HttpParserSerializer private constructor() {
    fun parse(httpData: String): Map<String, String> {
        return Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(httpData)
    }

    fun serialize(httpMessage: HttpMessage): String {
        return httpMessage.serialize()
    }

    companion object {
        val instance: HttpParserSerializer by lazy { HttpParserSerializer() }
    }
}