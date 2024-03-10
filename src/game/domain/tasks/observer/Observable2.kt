package game.domain.tasks.observer

import game.utils.dev.Log

open class Observable2 {
    @JvmField
    protected var observers: MutableSet<Observer2> = HashSet()
    fun notifyObservers(arg: Any?) {
        Log.i("notifyObservers $arg, $observers")
        for (o in observers) o.update(arg)
    }

    fun register(o: Observer2) {
        observers.add(o)
    }

    fun unregister(o: Observer2) {
        observers.remove(o)
    }

    fun unregisterAll() {
        observers.clear()
    }

    fun notify(o: Observer2, arg: Any?) {
        o.update(arg)
    }
}