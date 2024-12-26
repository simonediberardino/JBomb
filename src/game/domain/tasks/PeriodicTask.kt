package game.domain.tasks

import game.utils.dev.Log
import game.utils.time.now
import kotlinx.coroutines.*

class PeriodicTask(
    private val callback: () -> Unit,
    private val delay: Long,
    private val scope: CoroutineScope
) {
    private var job: Job? = null
    private var lastUpdate: Long = 0L
    private var currDelay: Long = delay

    fun start() {
        job = scope.launch {
            while (isActive) {
                val lastDelay = now() - lastUpdate

                try {
                    callback()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                    // Skip delay adjustment and retry
                    continue
                }

                if (lastUpdate != 0L) {
                    val adjustment = lastDelay - delay
                    currDelay = (currDelay - adjustment)
                        .coerceIn(delay / 10L, delay * 10L)
                }

                Log.i("[PeriodicTask] run with delay=$currDelay")

                lastUpdate = now()

                // Ensure delay is valid
                try {
                    delay(currDelay)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            Log.i("[PeriodicTask] cancelled")
        }
    }


    fun resume() {
        lastUpdate = 0L
        currDelay = delay
        start()
    }

    fun stop() {
        job?.cancel()
    }
}