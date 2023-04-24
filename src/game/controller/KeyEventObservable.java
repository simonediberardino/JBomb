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

public class KeyEventObservable extends Observable implements KeyListener {
    private static final int KEY_W = KeyEvent.VK_W;
    private static final int KEY_A = KeyEvent.VK_A;
    private static final int KEY_S = KeyEvent.VK_S;
    private static final int KEY_D = KeyEvent.VK_D;
    private static final int KEY_SPACE = KeyEvent.VK_SPACE;
    private static final int KEY_DELAY_MS = 100;

    private static final Map<Integer, Command> keyAssignment = Map.ofEntries(
            entry(KEY_W, Command.MOVE_UP),
            entry(KEY_A, Command.MOVE_LEFT),
            entry(KEY_S, Command.MOVE_DOWN),
            entry(KEY_D, Command.MOVE_RIGHT),
            entry(KEY_SPACE, Command.PLACE_BOMB)
    );

    private final Map<Command, Long> commandEventsTime = new HashMap<>();

    @Override
    public void keyPressed(KeyEvent e) {
        Command action = keyAssignment.get(e.getKeyCode());
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
