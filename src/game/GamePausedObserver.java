package game;

import game.controller.Command;
import game.events.Observer2;

import java.util.Observable;
import java.util.Observer;

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
