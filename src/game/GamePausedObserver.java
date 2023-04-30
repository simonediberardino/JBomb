package game;

import game.controller.Command;

import java.util.Observable;
import java.util.Observer;

public class GamePausedObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof Command)) {
            return;
        }

        if(arg == Command.PAUSE){
            BomberMan.getInstance().toggleGameState();
        }
    }
}
