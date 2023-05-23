package game.events;

import game.data.DataInputOutput;

public class RoundPassedGameEvent implements GameEvent{
    @Override
    public void invoke(Object arg) {
        DataInputOutput.increaseRounds();
        DataInputOutput.decreaseLives();
    }
}
