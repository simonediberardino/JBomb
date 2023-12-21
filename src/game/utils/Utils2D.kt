package game.utils

import java.awt.Component
import java.awt.Container
import java.awt.Image
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import kotlin.math.max


object Utils2D {
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
