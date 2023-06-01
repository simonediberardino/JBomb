package game.events;

import game.Bomberman;
import game.data.DataInputOutput;

import static game.data.DataInputOutput.increaseExplosionLength;
import static game.data.DataInputOutput.updateStoredPlayerData;

public class ExplosionLengthPowerUpEvent implements GameEvent{
    @Override
    public void invoke(Object arg) {
        increaseExplosionLength();
    }
}
