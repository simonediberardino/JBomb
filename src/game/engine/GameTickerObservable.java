package game.engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class GameTickerObservable extends Observable {
    private final static int DELAY_MS = 10;
    public GameTickerObservable() {
        ActionListener taskPerformer = evt -> {
            setChanged();
            notifyObservers();
        };

        new Timer(DELAY_MS, taskPerformer).start();
    }
}
