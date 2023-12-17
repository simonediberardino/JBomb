package game.events.game;

import game.Bomberman;
import game.events.models.GameEvent;

/**
 * Fires a game event using Strategy Pattern;
 */
public class UpdateCurrentAvailableBombsEvent implements GameEvent {
    @Override
    public void invoke(Object arg) {
        if (Bomberman.getMatch().getPlayer() == null)
            return;

        Bomberman.getMatch().getCurrentLevel().onUpdateCurrentAvailableBombsEvent((int) arg);
        Bomberman.getMatch().updateInventoryWeaponController();
    }
}
