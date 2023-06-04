package game.hardwareinput;

import game.entity.models.Direction;

public enum Command {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_RIGHT,
    MOVE_LEFT,
    PLACE_BOMB,
    PAUSE,
    INTERACT;

    public Direction commandToDirection(){
        return switch (this) {
            case MOVE_UP -> Direction.UP;
            case MOVE_DOWN -> Direction.DOWN;
            case MOVE_LEFT -> Direction.LEFT;
            case MOVE_RIGHT -> Direction.RIGHT;
            default -> null;
        };
    }
}
