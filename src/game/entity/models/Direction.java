package game.entity.models;

import game.hardwareinput.Command;

public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public Command toCommand(){
        return switch (this) {
            case UP -> Command.MOVE_UP;
            case DOWN -> Command.MOVE_DOWN;
            case LEFT -> Command.MOVE_LEFT;
            case RIGHT -> Command.MOVE_RIGHT;
        };
    }

    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
