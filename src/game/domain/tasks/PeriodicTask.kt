package game.domain.tasks

import game.utils.time.now
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            while (true) {
                val lastDelay = now() - lastUpdate

                callback()

                if (lastUpdate == 0L) {
                    currDelay = delay
                } else {
                    // effective delay may not be the same depending on cpu usage,
                    // dynamically reduce it to be effective $delay
                    val adjustment = lastDelay - delay
                    currDelay -= adjustment
                }

                lastUpdate = now()
                delay(currDelay)
            }
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