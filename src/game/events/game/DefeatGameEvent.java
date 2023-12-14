package game.events.game;

import game.Bomberman;
import game.events.models.GameEvent;

/**
 * Fires a game event using Strategy Pattern;
 */
public class DefeatGameEvent implements GameEvent {
    @Override
    public void invoke(Object arg) {
        Bomberman.getMatch().getCurrentLevel().onDefeatGameEvent();
    }
}
