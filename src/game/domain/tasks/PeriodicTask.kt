package game.domain.tasks

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PeriodicTask(
        private val callback: () -> Unit,
        var delay: Long,
        private val scope: CoroutineScope
) {
    private var job: Job? = null

    fun start() {
        job = scope.launch {
            while (true) {
                callback()
                delay(delay)
            }
        }
    }

    fun resume() {
        start()
    }

    fun stop() {
        job?.cancel()
    }
}