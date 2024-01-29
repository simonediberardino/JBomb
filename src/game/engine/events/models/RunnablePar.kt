package game.engine.events.models

interface RunnablePar {
    fun <T> execute(par: T): Any?
}