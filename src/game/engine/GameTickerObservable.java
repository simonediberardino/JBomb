package game.engine;

import game.BomberManMatch;
import game.entity.models.Entity;

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
public class GameTickerObservable extends Observable {
    private final Set<Observer> observerSet = new HashSet<>();
    private final int DELAY_MS = 20;
    /**
     This ActionListener updates observers of the GameTickerObservable periodically based on the specified delay. It loops through
     each observer in the observerSet to check if the delay has passed since the last update. If the delay has passed, it calls the
     update method of the observer with the current GameState object.
     */
    private final ActionListener taskPerformer = evt -> {
        setChanged(); // mark the Observable as having changed
        for (Observer observer : observerSet.toArray(new Observer[0])) { // loop through each observer in the observerSet
            if(observer instanceof Entity)
            if(!((Entity) observer).isSpawned()) deleteObserver(observer);
            boolean delayPassed = true;
            if (observer instanceof GameTickerObserver) { // check if the observer is of type GameTickerObserver
                GameTickerObserver gameTickerObserver = (GameTickerObserver) observer; // cast the observer to GameTickerObserver
                long lastUpdate = gameTickerObserver.getLastUpdate(); // get the last update time of the observer
                long delayObserverUpdate = (long) gameTickerObserver.getDelayObserverUpdate(); // get the delay time of the observer
                delayPassed = System.currentTimeMillis() - lastUpdate >= delayObserverUpdate; // check if the delay has passed since the last update
            }
            if (delayPassed) { // if the delay has passed
                observer.update(this, BomberManMatch.getInstance().getGameState()); // call the update method of the observer with the current GameState object
            }
        }
    };

    private final Timer timer = new Timer(DELAY_MS, taskPerformer);

    /**

     Adds an observer to the observer set.
     @param o the observer to add
     */
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        observerSet.add(o);
    }
    /**

     Removes an observer from the observer set.
     @param o the observer to remove
     */
    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
        observerSet.remove(o);
    }

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
