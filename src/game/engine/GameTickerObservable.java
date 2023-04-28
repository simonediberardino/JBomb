package game.engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**

 The GameTickerObservable class is an observable that notifies its observers
 periodically with a fixed delay of 10 milliseconds.
 It extends the Observable class.
 */
public class GameTickerObservable extends Observable {

    /**
     The delay between each notification in milliseconds.
     */
    private final static int DELAY_MS = 40;

    /**
     Constructs a GameTickerObservable instance that notifies its observers periodically
     with a fixed delay of 10 milliseconds.
     It creates an ActionListener to be triggered every DELAY_MS milliseconds, and calls
     setChanged() and notifyObservers() to notify its observers.
     */
    public GameTickerObservable() {
        ActionListener taskPerformer = evt -> {
            setChanged();
            notifyObservers();
        };

        new Timer(DELAY_MS, taskPerformer).start();
    }
}
