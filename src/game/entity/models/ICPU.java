package game.entity.models;

import game.models.Direction;

public interface ICPU {
    Direction chooseDirection(boolean forceChange);
    void changeDirection();
}
