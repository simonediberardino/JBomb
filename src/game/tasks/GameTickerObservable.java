package game.tasks;

import game.Bomberman;
import game.entity.models.Entity;
import game.events.Observable2;
import game.events.Observer2;
import game.utils.Utility;

/**
 * The GameTickerObservable class is an observable that notifies its observers periodically with a fixed delay
 * of DELAY_MS milliseconds, ignoring updates if a specific delay is not passed. It extends the Observable class.
 */
public class GameTickerObservable extends Observable2 {
    private final PeriodicTask periodicTask;
    private final int DELAY_MS = 20;
    /**
     * This ActionListener updates observers of the GameTickerObservable periodically based on the specified delay. It loops through
     * each observer in the observerSet to check if the delay has passed since the last update. If the delay has passed, it calls the
     * update method of the observer with the current GameState object.
     */
    private final Runnable task = () -> {
        Observer2[] array = observers.toArray(new Observer2[0]);

        // loop through each observer in the observerSet
        for (Observer2 observer : array) {
            if (observer instanceof Entity)
                if (!((Entity) observer).isSpawned()) unregister(observer);
            boolean delayPassed = true;

            if (observer instanceof GameTickerObserver) { // check if the observer is of type GameTickerObserver
                GameTickerObserver gameTickerObserver = (GameTickerObserver) observer; // cast the observer to GameTickerObserver
                long lastUpdate = gameTickerObserver.getLastUpdate(); // get the last update time of the observer
                long delayObserverUpdate = (long) gameTickerObserver.getDelayObserverUpdate(); // get the delay time of the observer
                delayPassed = Utility.timePassed(lastUpdate) >= delayObserverUpdate; // check if the delay has passed since the last update
            }

            if (delayPassed) { // if the delay has passed
                notify(observer, Bomberman.getMatch().getGameState());
            }
        }
    };

    public GameTickerObservable() {
        periodicTask = new PeriodicTask(task, DELAY_MS);
        periodicTask.start();
    }

    public void resume() {
        periodicTask.resume();
    }

    public void stop() {
        periodicTask.stop();
    }
}
