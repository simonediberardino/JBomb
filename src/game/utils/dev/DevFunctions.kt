package game.utils.dev

import game.domain.tasks.GameTickerObservable.Companion.DELAY_MS
import kotlinx.coroutines.delay

suspend fun waittillframeend() {
    delay(DELAY_MS.toLong())
}