package game.controller;

import game.Bomberman;
import game.engine.PeriodicTask;
import game.entity.Player;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.models.Direction;

import java.awt.event.*;
import java.util.ArrayList;

public class MouseControllerManager extends MouseAdapter {
    private boolean mousePressed;
    //first and last directions from player are differentiated so the task can be terminated when they have no direction in common, in order to avoid infinite loop
    private ArrayList<Direction> firstDirectionsFromPlayer = new ArrayList<>();
    ArrayList<Direction> latestDirectionsFromPlayer =new ArrayList<>();
    private Coordinates mouseCoords;
    private final int DELAY = 300;

    private final Runnable task = () -> {
        Player currPlayer = Bomberman.getMatch().getPlayer();

        onCooldown();
        //interact with mouse
        if (Bomberman.getMatch().getPlayer() != null && !currPlayer.getAliveState())
            return;

        Entity entity = Coordinates.getEntityOnCoordinates(mouseCoords);
        if (entity != null && Bomberman.getMatch().getPlayer().getListClassInteractWithMouse().contains(entity.getClass())) {
            entity.onMouseClick();
            stopPeriodicTask();
            return;
        }

        //TODO unregister observer
        //else move with mouse
        latestDirectionsFromPlayer = mouseCoords.fromCoordinatesToDirection(currPlayer.getCoords());
        for (Direction d : latestDirectionsFromPlayer) {
            Bomberman.getMatch().getControllerManager().onKeyPressed(d.toCommand());
        }

        if (firstDirectionsFromPlayer.isEmpty()
                || firstDirectionsFromPlayer.stream().anyMatch(d -> latestDirectionsFromPlayer.contains(d))
        ) {
            return;
        }

        stopPeriodicTask();
    };

    public PeriodicTask periodicTask = new PeriodicTask(task,DELAY);

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mouseCoords = new Coordinates(e.getX(), e.getY());
        firstDirectionsFromPlayer = mouseCoords.fromCoordinatesToDirection(Bomberman.getMatch().getPlayer().getCoords());
        periodicTask.resume();
    }

    //directions are refreshed and will be replaced in task
    public void onCooldown(){
        for (Direction d: firstDirectionsFromPlayer) {
            Bomberman.getMatch().getControllerManager().onKeyReleased(d.toCommand());
        }
    }

    public void stopPeriodicTask(){
        if(!mousePressed) return;
        periodicTask.stop();
        onCooldown();
        mousePressed = false;
    }
}
