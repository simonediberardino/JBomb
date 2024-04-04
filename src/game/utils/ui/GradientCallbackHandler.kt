package game.utils.ui

import javax.swing.Timer

class GradientCallbackHandler(
        private val start: Float,
        private val end: Float,
        private val nonNegativeStep: Float,
        private val p: (Float) -> Unit
) {
    private var currValue = start
    private lateinit var t: Timer
    private val sign: Int = if (start > end) {
        -1
    } else 1

    init {
        if (nonNegativeStep < 0)
            throw RuntimeException("step must be non negative")
    }

    fun execute() {
        t = Timer(1) {
            p(currValue)

            val step = sign * nonNegativeStep

            currValue += step

            val hasFinished = if (start >= end)
                currValue < end
            else currValue >= end

            if (hasFinished)
                t.stop()
        }

        t.start()
    }
}