package game.events.game;

import game.Bomberman;
import game.events.models.GameEvent;

/**
 * Fires a game event using Strategy Pattern;
 */
public class ScoreGameEvent implements GameEvent {
    @Override
    public void invoke(Object arg) {
        Bomberman.getMatch().getCurrentLevel().onScoreGameEvent((int) arg);
    }
}
