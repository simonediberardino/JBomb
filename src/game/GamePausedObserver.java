package game;

import game.hardwareinput.Command;
import game.events.Observer2;

public class GamePausedObserver implements Observer2 {
    @Override
    public void update(Object arg) {
        if (!(arg instanceof Command)) {
            return;
        }

        if(arg == Command.PAUSE){
            Bomberman.getMatch().toggleGameState();
        }
    }
}
