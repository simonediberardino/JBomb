package game.utils.dev

import game.domain.tasks.GameTickerObservable.Companion.DELAY_MS
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout

suspend fun waittillframeend() {
    delay(DELAY_MS.toLong())
}

suspend fun suspendCoroutineWithTimeout() {
    withTimeout(DELAY_MS.toLong()) {

    }
}