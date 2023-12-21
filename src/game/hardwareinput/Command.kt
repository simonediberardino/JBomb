package game.hardwareinput

import game.entity.models.Direction

enum class Command {
    MOVE_UP, MOVE_DOWN, MOVE_RIGHT, MOVE_LEFT, ATTACK, PAUSE, INTERACT;

    fun commandToDirection(): Direction? {
        return when (this) {
            MOVE_UP -> Direction.UP
            MOVE_DOWN -> Direction.DOWN
            MOVE_LEFT -> Direction.LEFT
            MOVE_RIGHT -> Direction.RIGHT
            else -> null
        }
    }
    fun isMovementKey(): Boolean {
        return arrayOf(MOVE_UP, MOVE_DOWN, MOVE_RIGHT, MOVE_LEFT).contains(this)
    }
}