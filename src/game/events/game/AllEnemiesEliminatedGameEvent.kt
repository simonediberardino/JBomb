package game.events.game;

import game.Bomberman;
import game.events.models.GameEvent;

public class AllEnemiesEliminatedGameEvent implements GameEvent {
    @Override
    public void invoke(Object arg) {
        Bomberman.getMatch().getCurrentLevel().onAllEnemiesEliminated();
    }
}
