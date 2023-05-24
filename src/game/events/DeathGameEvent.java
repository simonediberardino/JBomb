package game.events;

import game.Bomberman;
import game.data.DataInputOutput;

public class DeathGameEvent implements GameEvent{
    @Override
    public void invoke(Object arg) {
        DataInputOutput.increaseDeaths();
        DataInputOutput.decreaseLives();
        DataInputOutput.decreaseScore(1000);
    }
}
