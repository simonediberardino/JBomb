package game.engine;

import game.BomberManMatch;
import game.Bomberman;
import game.entity.models.Entity;
import game.events.Observable2;
import game.events.Observer2;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 The GameTickerObservable class is an observable that notifies its observers periodically with a fixed delay
 of DELAY_MS milliseconds, ignoring updates if a specific delay is not passed. It extends the Observable class.
 */
public class GameTickerObservable extends Observable2 {
    private final int DELAY_MS = 20;
    /**
     This ActionListener updates observers of the GameTickerObservable periodically based on the specified delay. It loops through
     each observer in the observerSet to check if the delay has passed since the last update. If the delay has passed, it calls the
     update method of the observer with the current GameState object.
     */
    private final ActionListener taskPerformer = evt -> {
        for (Observer2 observer : observers.toArray(new game.events.Observer2[0])) { // loop through each observer in the observerSet
            if(observer instanceof Entity)
            if(!((Entity) observer).isSpawned()) unregister(observer);
            boolean delayPassed = true;
            if (observer instanceof GameTickerObserver) { // check if the observer is of type GameTickerObserver
                GameTickerObserver gameTickerObserver = (GameTickerObserver) observer; // cast the observer to GameTickerObserver
                long lastUpdate = gameTickerObserver.getLastUpdate(); // get the last update time of the observer
                long delayObserverUpdate = (long) gameTickerObserver.getDelayObserverUpdate(); // get the delay time of the observer
                delayPassed = System.currentTimeMillis() - lastUpdate >= delayObserverUpdate; // check if the delay has passed since the last update
            }
            if (delayPassed) { // if the delay has passed
                observer.update(Bomberman.getMatch().getGameState()); // call the update method of the observer with the current GameState object
            }
        }
    };

    private final Timer timer = new Timer(DELAY_MS, taskPerformer);


    /**
     Creates a new GameTickerObservable and starts the timer.
     */
    public GameTickerObservable() {
        timer.start();
    }

    /**
     Starts the timer.
     */
    public void start() {
        timer.start();
    }
    /**

     Stops the timer.
     */
    public void stop() {
        timer.stop();
    }
}
