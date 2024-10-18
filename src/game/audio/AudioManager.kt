package game.audio

import com.sun.org.apache.xerces.internal.xinclude.XIncludeHandler.BUFFER_SIZE
import game.JBomb
import game.data.cache.Cache
import game.data.data.DataInputOutput
import game.properties.RuntimeProperties
import game.utils.byte_utils.ByteUtils.readAllBytes
import game.utils.dev.Log
import game.utils.file_system.Paths.defaultSoundTrack
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.*
import javax.sound.sampled.*
import kotlin.math.abs
import kotlin.math.max


class AudioManager private constructor() {
    // Hashmap containing data of current playing sounds
    private val audioHashMap = HashMap<String, LinkedList<Pair<SourceDataLine, AudioInputStream>>>()
    private var currentBackgroundSong = ""

    /**
     * Plays the specified sound model.
     *
     * @param soundModel The sound model to play.
     * @param loop       Determines whether the sound should be played in a loop.
     * @return
     */
    @JvmOverloads
    fun play(soundModel: SoundModel, loop: Boolean = false) {
        play(soundModel.toString(), loop, calculateVolumeFromStorage())
    }

    fun play(soundModel: SoundModel, loop: Boolean, volumePercentage: Int) {
        play(soundModel.toString(), loop, volumePercentage)
    }

    @JvmOverloads
    fun play(
            sound: String,
            loop: Boolean,
            volumePercentage: Int = calculateVolumeFromStorage()
    ) {
        if (RuntimeProperties.dedicatedServer)
            return

        val cache = Cache.instance

        JBomb.scope.launch {
            try {
                var audioData: ByteArray?

                // Check if the audio data is already in the cache
                if (cache.hasInCache(sound)) {
                    audioData = cache.queryCache<ByteArray>(sound)
                } else {
                    // If the audio data is not in the cache, load it from the resource stream
                    BufferedInputStream(javaClass.getResourceAsStream(String.format("/%s", sound))).use { `in` ->
                        audioData = readAllBytes(`in`)
                        cache.saveInCache(sound, audioData)
                    }
                }

                ByteArrayInputStream(audioData).use { byteArrayInputStream ->
                    AudioSystem.getAudioInputStream(byteArrayInputStream).use { audioIn ->
                        val format: AudioFormat = audioIn.format

                        val info = DataLine.Info(SourceDataLine::class.java, format)

                        val audioLine = AudioSystem.getLine(info) as SourceDataLine
                        audioLine.open(format)

                        // Set the volume
                        val volumeControl = audioLine.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
                        val maxVolume = volumeControl.maximum
                        val minVolume = volumeControl.minimum
                        val diffVolume = abs((maxVolume - minVolume).toDouble()).toFloat()
                        val volumeValue = minVolume + diffVolume * (volumePercentage / 100f)
                        volumeControl.setValue(volumeValue)

                        addSoundToHashMap(sound, Pair(audioLine, audioIn))

                        audioLine.start()

                        val bytesBuffer = ByteArray(4096)
                        var bytesRead = -1

                        while (audioIn.read(bytesBuffer).also { bytesRead = it } != -1) {
                            audioLine.write(bytesBuffer, 0, bytesRead)
                        }

                        // If looping, continuously write the audio data
                        if (loop) {
                            while (true) {
                                audioIn.reset()
                                while (audioIn.read(bytesBuffer).also { bytesRead = it } != -1) {
                                    audioLine.write(bytesBuffer, 0, bytesRead)
                                }
                            }
                        }

                        audioLine.drain()
                        audioLine.close()
                        audioIn.close()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: UnsupportedAudioFileException) {
                e.printStackTrace()
            } catch (e: LineUnavailableException) {
                e.printStackTrace()
            } finally {
                // Close the SourceDataLine when done
                stop(sound)
            }
        }
    }

    private fun calculateVolumeFromStorage(): Int {
        val storedVolume = DataInputOutput.getInstance().volume
        val percentage = (storedVolume.toDouble() + 5) / 10.0 * 100.0
        return max(percentage, 0.0).toInt() // Ensure the percentage is not less than 0%
    }

    private fun addSoundToHashMap(soundModelString: String, obj: Pair<SourceDataLine, AudioInputStream>) {
        val tempList: MutableList<Pair<SourceDataLine, AudioInputStream>>? = audioHashMap[soundModelString]
        if (tempList == null) {
            audioHashMap[soundModelString] = LinkedList(linkedSetOf(obj))
            return
        }

        tempList.add(obj)
    }

    private fun removeSoundFromHashMap(soundModelString: String): Pair<SourceDataLine, AudioInputStream>? {
        val map = audioHashMap[soundModelString] ?: return null
        val dataLine = map.pop()
        if (map.isEmpty()) audioHashMap.remove(soundModelString)
        return dataLine
    }

    private fun stopAllInstancesOfSound(soundModelString: String) {
        val iterator = audioHashMap[soundModelString]?.iterator() ?: return

        while (iterator.hasNext()) {
            val pair = iterator.next()
            stop(pair)
            iterator.remove()
        }
    }

    @JvmOverloads
    fun playBackgroundSong(newSong: String = defaultSoundTrack) {
        if (currentBackgroundSong == newSong)
            return

        if (currentBackgroundSong.isNotEmpty()) {
            stopAllInstancesOfSound(currentBackgroundSong)
        }

        currentBackgroundSong = newSong
        play(currentBackgroundSong, true)
    }

    fun stopBackgroundSong() {
        stopAllInstancesOfSound(currentBackgroundSong)
        currentBackgroundSong = ""
    }

    fun stop(soundModelString: String) {
        stop(removeSoundFromHashMap(soundModelString) ?: return)
    }

    private fun stop(data: Pair<SourceDataLine, AudioInputStream>) {
        data.first.stop()
        data.first.drain()
        data.second.close()
    }

    companion object {
        private const val MAX_VOLUME_MANAGER = 20
        private const val MAX_VOLUME = 5f
        private const val MIN_VOLUME = -5f
        @JvmStatic
        val instance: AudioManager by lazy { AudioManager() }
    }
}
