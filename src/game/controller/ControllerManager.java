
package game.controller;

import game.Bomberman;
import game.engine.PeriodicTask;
import game.events.Observable2;
import game.utils.Utility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import static java.util.Map.entry;

/**
 This class represents an Observable object that observes key events and notifies its observers of the
 corresponding command that should be executed based on the key that was pressed.
 */
public class ControllerManager extends Observable2 implements KeyListener {
    private static ControllerManager instance;
    private static final int KEY_W = KeyEvent.VK_W;
    private static final int KEY_A = KeyEvent.VK_A;
    private static final int KEY_S = KeyEvent.VK_S;
    private static final int KEY_D = KeyEvent.VK_D;
    private static final int KEY_SPACE = KeyEvent.VK_SPACE;
    private static final int KEY_ESC = KeyEvent.VK_ESCAPE;
    private static int KEY_DELAY_MS = setDefaultCommandDelay();
    public Set<Command> commandQueue = new HashSet<>();

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
    private PeriodicTask task;

    public ControllerManager(){
        instance = this;
        setupTask();
    }

    public void onKeyPressed(Command action){
        // Ignore the event if the time elapsed since the last event is less than KEY_DELAY_MS
        if(Utility.timePassed( commandEventsTime.getOrDefault(action, 0L)) < KEY_DELAY_MS) return;

        if(action != null) {
            commandEventsTime.put(action, System.currentTimeMillis());
            commandQueue.add(action);
        }

        resume();

    }

    /**
     * Handles key pressed events and notifies observers with the corresponding command that should be executed.
     *
     * @param e the KeyEvent object that contains the information of the key that was pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Command action = keyAssignment.get(e.getKeyCode());
        onKeyPressed(action);
        // if a button is pressed, mouse movement gets interrupted
        Bomberman.getMatch().getMouseControllerManager().stopPeriodicTask();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Command action = keyAssignment.get(e.getKeyCode());
        onKeyReleased(action);

    }
    public void onKeyReleased(Command action){
        commandQueue.remove(action);
        if(commandQueue.isEmpty()) stop();
    }

    private void setupTask() {
        task = new PeriodicTask(() -> {
            for (Command command : new HashSet<>(commandQueue)) {
                notifyObservers(command);
            }
        }, KEY_DELAY_MS);

        task.start();
    }

    private void resume() {
        try{
            if(task != null) {
                task.resume();
            }
        }catch (Exception ignored){}
    }

    private void stop() {
        try{
            if(task != null) task.stop();
        }catch (Exception ignored){}
    }

    public boolean isCommandPressed(Command c) {
        return commandQueue.contains(c);
    }

    private void updateDelay() {
        instance.task.setDelay(KEY_DELAY_MS);
    }

    public static int decreaseCommandDelay() {
        KEY_DELAY_MS = 15;
        if (instance != null) {
            instance.updateDelay();
        }
        return KEY_DELAY_MS;
    }

    public static int setDefaultCommandDelay(){
        KEY_DELAY_MS = 30;
        if (instance != null) {
            instance.updateDelay();
        }
        return KEY_DELAY_MS;
    }

    @Override public void keyTyped(KeyEvent e) {}
}