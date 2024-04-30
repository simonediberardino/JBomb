package game.utils.ui

import java.awt.*
import java.awt.image.BufferedImage
import kotlin.math.max


object Utils2D {
    fun splitStringIntoChunks(
            input: String,
            chunkSize: Int,
            newLineDelimiter: Boolean = true
    ): List<String> {
        val result = mutableListOf<String>()
        var currCharIndex = 0
        var string = ""

        for ((index, c) in input.withIndex()) {
            string += c

            if (currCharIndex >= chunkSize) {
                if (newLineDelimiter && index < input.length - 1) {
                    val isWordNotComplete = input[index + 1].isLetterOrDigit()
                    if (isWordNotComplete)
                        string += "-"
                }

                result.add(string)
                string = ""
                currCharIndex = 0
                continue
            }

            currCharIndex++
        }

        if (string.isNotEmpty()) {
            result.add(string)
        }

        return result
    }

    fun stringWidth(font: Font, text: String): Int {
        // Create a temporary Graphics object to obtain FontMetrics
        // Create a temporary Graphics object to obtain FontMetrics
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val gd = ge.defaultScreenDevice
        val gc = gd.defaultConfiguration
        val img = gc.createCompatibleImage(1, 1, Transparency.TRANSLUCENT)
        val g2d = img.createGraphics()
        val fontMetrics = g2d.getFontMetrics(font)

        // Calculate the width of the character

        // Calculate the width of the character
        val charWidth = fontMetrics.stringWidth(text)

        // Dispose the Graphics object

        // Dispose the Graphics object
        g2d.dispose()

        return charWidth
    }

    fun getScaledImage(srcImg: Image, w: Int, h: Int): Image {
        val resizedImg = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
        val g2 = resizedImg.createGraphics()
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        g2.drawImage(srcImg, 0, 0, w, h, null)
        g2.dispose()
        return resizedImg
    }

    // Recursive method to calculate the maximum width of a component and its children
    fun calculateMaxWidth(component: Component): Int {
        return if (component is Container) {
            var maxWidth = component.preferredSize.width
            for (child in component.components) {
                val childMaxWidth = calculateMaxWidth(child)
                maxWidth = max(maxWidth.toDouble(), childMaxWidth.toDouble()).toInt()
            }
            maxWidth
        } else {
            component.width
        }
    }
}
