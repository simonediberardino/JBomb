package game.events;

import game.Bomberman;
import game.data.DataInputOutput;
import game.entity.models.Character;

public class KilledEnemyEvent implements GameEvent{
    @Override
    public void invoke(Object arg) {
        DataInputOutput.increaseKills();
    }
}
