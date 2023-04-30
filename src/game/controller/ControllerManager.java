
package game.controller;
import game.BomberMan;
import game.models.Direction;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.*;
import java.util.function.Predicate;

import static java.util.Map.entry;

/**
 This class represents an Observable object that observes key events and notifies its observers of the
 corresponding command that should be executed based on the key that was pressed.
 */
public class ControllerManager extends Observable implements KeyListener {
    private static final int KEY_W = KeyEvent.VK_W;
    private static final int KEY_A = KeyEvent.VK_A;
    private static final int KEY_S = KeyEvent.VK_S;
    private static final int KEY_D = KeyEvent.VK_D;
    private static final int KEY_SPACE = KeyEvent.VK_SPACE;
    private static final int KEY_ESC = KeyEvent.VK_ESCAPE;
    private static final int KEY_DELAY_MS = 30;
    private Set<Command> commandQueue = new HashSet<>();
    private Timer timer;

    // Key-Command mapping
    private static final Map<Integer, Command> keyAssignment = Map.ofEntries(
            entry(KEY_W, Command.MOVE_UP),
            entry(KEY_A, Command.MOVE_LEFT),
            entry(KEY_S, Command.MOVE_DOWN),
            entry(KEY_D, Command.MOVE_RIGHT),
            entry(KEY_SPACE, Command.PLACE_BOMB),
            entry(KEY_ESC, Command.PAUSE)
    );

    // Stores the time of the last key event for each command
    private final Map<Command, Long> commandEventsTime = new HashMap<>();

    /**
     * Handles key pressed events and notifies observers with the corresponding command that should be executed.
     *
     * @param e the KeyEvent object that contains the information of the key that was pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Command action = keyAssignment.get(e.getKeyCode());
        // Ignore the event if the time elapsed since the last event is less than KEY_DELAY_MS
        if(System.currentTimeMillis() - commandEventsTime.getOrDefault(action, 0L) < KEY_DELAY_MS) return;

        if(action != null) {
            commandQueue.add(action);
            if(timer == null || !timer.isRunning()){
                executeKeys();
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {
        Command action = keyAssignment.get(e.getKeyCode());
        commandQueue.remove(action);
        if(commandQueue.isEmpty()) stopExecuting();
    }

    private void executeKeys() {
        ActionListener taskPerformer = evt -> {
            for (Command c : commandQueue) {
                setChanged();
                notifyObservers(c);
            }
        };

        timer = new Timer(KEY_DELAY_MS, taskPerformer);
        timer.start();
    }

    private void stopExecuting() {
        if(timer != null) timer.stop();
    }

    public boolean isCommandPressed(Command c) {
        return commandQueue.contains(c);
    }

    @Override public void keyTyped(KeyEvent e) {}
}