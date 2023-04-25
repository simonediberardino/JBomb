
package game.controller;
import game.BomberMan;
import game.models.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import static java.util.Map.entry;

/**
 This class represents an Observable object that observes key events and notifies its observers of the
 corresponding command that should be executed based on the key that was pressed.
 */
public class KeyEventObservable extends Observable implements KeyListener {
    private static final int KEY_W = KeyEvent.VK_W;
    private static final int KEY_A = KeyEvent.VK_A;
    private static final int KEY_S = KeyEvent.VK_S;
    private static final int KEY_D = KeyEvent.VK_D;
    private static final int KEY_SPACE = KeyEvent.VK_SPACE;
    private static final int KEY_DELAY_MS = 100;

    // Key-Command mapping
    private static final Map<Integer, Command> keyAssignment = Map.ofEntries(
            entry(KEY_W, Command.MOVE_UP),
            entry(KEY_A, Command.MOVE_LEFT),
            entry(KEY_S, Command.MOVE_DOWN),
            entry(KEY_D, Command.MOVE_RIGHT),
            entry(KEY_SPACE, Command.PLACE_BOMB)
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
            commandEventsTime.put(action, System.currentTimeMillis());
            setChanged();
            notifyObservers(action);
        }
    }

    @Override public void keyReleased(KeyEvent e) { }
    @Override public void keyTyped(KeyEvent e) {}
}