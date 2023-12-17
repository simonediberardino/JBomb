package game.utils

import game.events.models.RunnablePar
import java.awt.event.ActionEvent
import java.util.concurrent.atomic.AtomicReference
import javax.swing.Timer

class GradientCallbackHandler(
        private val p: RunnablePar,
        private val start: Float,
        private val end: Float,
        private val step: Float)
{
    private var t: Timer? = null
    fun execute() {
        val currValue = AtomicReference(start)
        t = Timer(1) { l: ActionEvent? ->
            currValue.updateAndGet { v: Float -> v - step }
            p.execute(currValue.get())
            val hasFinished = if (step >= 0) currValue.get() <= end else currValue.get() >= end
            if (hasFinished) t!!.stop()
        }
        t!!.start()
    }
}