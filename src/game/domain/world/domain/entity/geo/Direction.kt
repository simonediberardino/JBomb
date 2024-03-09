package game.domain.world.domain.entity.geo

import game.input.Command

enum class Direction {
    DOWN, LEFT, UP, RIGHT;

    fun toCommand(): Command {
        return when (this) {
            UP -> Command.MOVE_UP
            DOWN -> Command.MOVE_DOWN
            LEFT -> Command.MOVE_LEFT
            RIGHT -> Command.MOVE_RIGHT
        }
    }

    fun opposite(): Direction {
        return when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
    }
}