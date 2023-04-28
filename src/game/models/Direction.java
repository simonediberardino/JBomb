package game.models;

import game.controller.Command;

public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public Command toCommand(){
        switch (this) {
            case UP: return Command.MOVE_UP;
            case DOWN: return Command.MOVE_DOWN;
            case LEFT: return Command.MOVE_LEFT;
            case RIGHT: return Command.MOVE_RIGHT;
        }
        return Command.MOVE_UP;
    }
}
