package game.events;

import game.Bomberman;
import game.data.DataInputOutput;

import static game.data.DataInputOutput.increaseExplosionLength;

public class UpdateMaxBombsEvent implements GameEvent{
    @Override
    public void invoke(Object arg) {
        Bomberman.getMatch().getPlayer().setCurrentBombs((int) arg);
        DataInputOutput.setMaxBombs((int) arg);
        Bomberman.getMatch().getInventoryElementControllerBombs().setNumItems((int) arg);
    }
}
