package game.events;

import game.data.DataInputOutput;

public class DefeatGameEvent implements GameEvent{
    @Override
    public void invoke(Object arg) {
        DataInputOutput.increaseLost();
    }
}
