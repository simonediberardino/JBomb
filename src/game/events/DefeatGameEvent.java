package game.events;

import game.data.DataInputOutput;

/**
 * Fires a game event using Strategy Pattern;
 */
public class DefeatGameEvent implements GameEvent{
    @Override
    public void invoke(Object arg) {
        DataInputOutput.increaseLost();
    }
}
