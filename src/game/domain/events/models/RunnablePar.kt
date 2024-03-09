package game.domain.events.models

interface RunnablePar {
    fun <T> execute(par: T): Any?
}