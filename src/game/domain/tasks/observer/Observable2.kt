package game.domain.tasks.observer

open class Observable2 {
    @JvmField
    protected var observers: MutableSet<Observer2> = HashSet()

    fun notifyObservers(arg: ObserverParam) {
        for (o in observers) o.update(arg)
    }

    fun register(o: Observer2) {
        observers.add(o)
        onRegister(o)
    }

    fun unregister(o: Observer2) {
        observers.remove(o)
        onUnregister(o)
    }

    fun unregisterAll() {
        observers.clear()
        onUnregisterAll()
    }

    open fun onUnregisterAll() {}
    open fun onUnregister(o: Observer2) {}
    open fun onRegister(o: Observer2) {}

    fun notify(o: Observer2, arg: ObserverParam) {
        o.update(arg)
    }

    data class ObserverParam(
            val identifier: ObserverParamIdentifier,
            val value: Any?
    )

    enum class ObserverParamIdentifier {
        GAME_TICK,
        INPUT_COMMAND,
        DELETE_COMMAND,
    }
}