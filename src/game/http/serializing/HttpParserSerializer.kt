package game.http.serializing

import com.google.common.base.Splitter
import game.http.models.HttpMessage

class HttpParserSerializer private constructor() {
    fun parse(httpData: String): Map<String, String> {
        var httpData_: String = httpData

        if (httpData.startsWith("{"))
            httpData_ = httpData.drop(1)

        if (httpData.endsWith("}"))
            httpData_ = httpData_.dropLast(1)

        return Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(httpData_)
    }

    fun serialize(httpMessage: HttpMessage): String {
        return httpMessage.serialize()
    }

    companion object {
        val instance: HttpParserSerializer by lazy { HttpParserSerializer() }
    }
}