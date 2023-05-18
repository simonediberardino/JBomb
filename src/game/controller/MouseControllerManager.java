package game.controller;

import game.Bomberman;
import game.engine.PeriodicTask;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.models.Direction;

import java.awt.event.*;
import java.util.ArrayList;

public class MouseControllerManager implements MouseListener, MouseWheelListener, MouseMotionListener {
    private boolean mousePressed;
    //first and last directions from player are differentiated so the task can be terminated when they have no direction in common, in order to avoid infinite loop
    private ArrayList<Direction> firstDirectionsFromPlayer = new ArrayList<>();
    ArrayList<Direction> latestDirectionsFromPlayer =new ArrayList<>();
    private Coordinates mouseCoords;
    private int DELAY= 300;
    private Runnable task = ()->{
        onCooldown();
        //interact with mouse
        if (Bomberman.getMatch().getPlayer()!=null&& !Bomberman.getMatch().getPlayer().getAliveState()) return;
        Entity entity = Coordinates.getEntityOnCoordinates(mouseCoords);
        if (entity != null && Bomberman.getMatch().getPlayer().getListClassInteractWithMouse().contains(entity.getClass())) {
            entity.onMouseClick();
            stopPeriodicTask();
            return;
        }
        //else move with mouse
        latestDirectionsFromPlayer = mouseCoords.fromCoordinatesToDirection(Bomberman.getMatch().getPlayer().getCoords());
        for (Direction d : latestDirectionsFromPlayer
        ) {
            Bomberman.getMatch().getControllerManager().onKeyPressed(d.toCommand());
        }
        if(!firstDirectionsFromPlayer.isEmpty() && firstDirectionsFromPlayer.stream().noneMatch(d-> latestDirectionsFromPlayer.contains(d))){
            stopPeriodicTask();
        }


    };
    public PeriodicTask periodicTask = new PeriodicTask(task,DELAY);



    public MouseControllerManager(){

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mouseCoords = new Coordinates(e.getX(), e.getY());
        firstDirectionsFromPlayer = mouseCoords.fromCoordinatesToDirection(Bomberman.getMatch().getPlayer().getCoords());
        periodicTask.resume();

    }
    //task will only stop when in-task condition is true or when a button is pressed manually. mouse being release does not effect
    @Override public void mouseReleased(MouseEvent e) {
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
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    @Override public void mouseMoved(MouseEvent e) { }
    @Override public void mouseWheelMoved(MouseWheelEvent e) { }
}
