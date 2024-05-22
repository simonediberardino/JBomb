package game.utils.byte_utils

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object ByteUtils {
    @JvmStatic
    @Throws(IOException::class)
    fun readAllBytes(inputStream: InputStream): ByteArray {
        return inputStream.readBytes()
    }
}
