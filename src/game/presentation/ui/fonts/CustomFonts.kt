package game.presentation.ui.fonts

import game.data.cache.Cache
import game.data.cache.Cache.Companion.instance
import game.utils.byte_utils.ByteUtils.readAllBytes
import game.utils.file_system.Paths.fontsPath
import java.awt.Font
import java.awt.FontFormatException
import java.io.ByteArrayInputStream
import java.io.IOException

object CustomFonts {
    const val FONT_PIXELATED = "PixeloidMono-d94EV.ttf"
    private val path = fontsPath

    @JvmStatic
    fun getFont(fontName: String, fontSize: Float): Font {
        val fontPath = String.format("/%s/%s", path, fontName)
        val cache = Cache.instance

        if (cache.hasInCache(fontName)) {
            return cache.queryCache<Font>(fontName)!!.deriveFont(fontSize)
        }

        CustomFonts::class.java.getResourceAsStream(fontPath).use { inputStream ->
            if (inputStream == null) {
                throw IOException("Font resource not found: $fontPath")
            }

            // Read the InputStream into a byte array
            val fontData = readAllBytes(inputStream)
            val font = Font.createFont(Font.TRUETYPE_FONT, ByteArrayInputStream(fontData))
            cache.saveInCache(fontName, font)

            return font.deriveFont(fontSize)
        }
    }
}
