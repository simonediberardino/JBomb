package game.events.game;

import game.Bomberman;
import game.events.models.GameEvent;

public class UpdateCurrentBombsLengthEvent implements GameEvent {
    @Override
    public void invoke(Object arg) {
        Bomberman.getMatch().getCurrentLevel().onUpdateBombsLengthEvent(Bomberman.getMatch().getPlayer(), (int) arg);
    }
}
