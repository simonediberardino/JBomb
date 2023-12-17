package game.events.game;

import game.Bomberman;
import game.events.models.GameEvent;

public class DeathGameEvent implements GameEvent {
    @Override
    public void invoke(Object arg) {
        Bomberman.getMatch().getCurrentLevel().onDeathGameEvent();
    }
}
