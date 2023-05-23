package game.events;

import game.Bomberman;
import game.data.DataInputOutput;
import game.entity.models.Character;

public class ScoreGameEvent implements GameEvent{
    @Override
    public void invoke(Object arg) {
        DataInputOutput.increaseScore((Integer) arg);
        Bomberman.getMatch().getInventoryElementControllerPoints().setNumItems((int) DataInputOutput.getScore());
    }
}
