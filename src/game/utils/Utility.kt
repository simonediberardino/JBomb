package game.utils

import game.JBomb
import game.data.cache.Cache
import game.data.cache.Cache.Companion.instance
import game.utils.dev.Log
import game.utils.time.now
import game.values.Dimensions
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.io.IOException
import java.lang.RuntimeException
import javax.imageio.ImageIO

/**
 * A utility class containing helper methods for the game.
 */
object Utility {
    
    fun ensureRange(value: Float, min: Float, max: Float): Float {
        return value.coerceAtLeast(min).coerceAtMost(max)
    }

    
    fun timePassed(time: Long): Long {
        return now() - time
    }

    fun isValueInRange(value: Int, min: Int, max: Int): Boolean {
        return value in min..max
    }

    /**
     * Converts a dimension in pixels to a dimension in screen units, based on the default screen size.
     *
     * @param dim The dimension in pixels to be converted.
     * @return The converted dimension in screen units.
     */
    
    fun px(dim: Int): Int {
        return px(dim.toDouble()).toInt()
    }

    
    val screenSize: Dimension
        get() = Toolkit.getDefaultToolkit().screenSize

    fun px(dim: Double): Double {
        val frame = JBomb.JBombFrame
        val screenSize = if (frame == null ) Toolkit.getDefaultToolkit().screenSize else frame.preferredSize
        return dim * (screenSize.getWidth() / Dimensions.DEFAULT_SCREEN_SIZE.getWidth())
    }

    
    fun fileExists(filePath: String?): Boolean {
        val classLoader = Thread.currentThread().contextClassLoader
        try {
            classLoader.getResourceAsStream(filePath).use { imageStreamWithStatus -> return imageStreamWithStatus != null }
        } catch (e: IOException) {
            return false
        }
    }

    /**
     * Loads an image from a file with the given file name.
     *
     * @param fileName The file name of the image to be loaded.
     * @return The loaded image, or null if the file could not be found or read.
     */
    
    fun loadImage(fileName: String): BufferedImage? {
        var fileName = fileName
        val cache = Cache.instance

        if (cache.hasInCache(fileName)) {
            return cache.queryCache<BufferedImage>(fileName) ?: throw RuntimeException()
        }

        // Use ClassLoader to load the image from the JAR file
        fileName = fileName.replace("/src", "")
        Log.i("Loading $fileName")
        val inputStream = Utility::class.java.getResourceAsStream("/$fileName") ?: throw RuntimeException()

        val image = ImageIO.read(inputStream) ?: throw RuntimeException()
        instance.saveInCache(fileName, image)
        return image
    }

    private fun chooseRandom(chance: Int): Boolean {
        var chance = chance
        chance = 0.coerceAtLeast(chance)
        chance = 100.coerceAtMost(chance)
        return Math.random() * 100 <= chance
    }

    
    fun runPercentage(chance: Int, runnable: Runnable) {
        if (chooseRandom(chance)) runnable.run()
    }
}