package game.controller;

import game.Bomberman;
import game.engine.PeriodicTask;
import game.entity.Player;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.models.Direction;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class MouseControllerManager extends MouseAdapter implements MouseMotionListener {
    public boolean mouseDraggedInteractionOccured;
    public boolean mouseDraggedInteractionInterrupted;
    public boolean mouseClickedInteractionSuccessful;
    public boolean mouseDragInteractionEntered = false;
    public boolean isMouseDragged;
    public boolean isMouseClicked;
    private LinkedList<MouseEvent> mouseCLickQueue = new LinkedList<>();
    //first and last directions from player are differentiated so the task can be terminated when they have no direction in common, in order to avoid infinite loop
    private ArrayList<Direction> firstDirectionsFromPlayer = new ArrayList<>();
    ArrayList<Direction> latestDirectionsFromPlayer =new ArrayList<>();
    public Coordinates mouseCoords;
    private final int DELAY = 300;
    private Entity entity = null;

    private final Runnable task = () -> {
        Player currPlayer = Bomberman.getMatch().getPlayer();
        if(currPlayer==null)return;
        onCooldown();
        //interact with mouse
        if (Bomberman.getMatch().getPlayer() != null && !currPlayer.getAliveState())
            return;


        if (entity != null && Bomberman.getMatch().getPlayer().getListClassInteractWithMouse().contains(entity.getClass())) {
            entity.mouseInteractions();
            onPeriodicTaskEnd();
            nextCommandInQueue();
            return;
        }


        //TODO unregister observer
        //else move with mouse
        else {
            latestDirectionsFromPlayer = mouseCoords.fromCoordinatesToDirection(currPlayer.getCoords());
            for (Direction d : latestDirectionsFromPlayer) {
                Bomberman.getMatch().getControllerManager().onKeyPressed(d.toCommand());
            }

            if (firstDirectionsFromPlayer.isEmpty()
                    || firstDirectionsFromPlayer.stream().anyMatch(d -> latestDirectionsFromPlayer.contains(d))
            ) {
                return;
            }
            onPeriodicTaskEnd();
            nextCommandInQueue();
        }

    };
    public void nextCommandInQueue(){
        mouseCLickQueue.poll();
        if(!mouseCLickQueue.isEmpty())
        onMouseClicked(mouseCLickQueue.peekFirst());
    }
    @Override
    public void mouseReleased(MouseEvent event){

        //if mouse dragged interaction was not successful and mouse is released, call mouseClicked so that click-dependent interactions can be called.
        //this is necessary because mouseClicked is never called when mouseDragged is called, unlike mousePressed. MouseClicked was still chosen because
        // the two methods shouldn't be called at exactly the same time.
        if(isMouseDragged) {
            if (!mouseDraggedInteractionOccured) mouseClicked(event);
            mouseDragInteractionEntered = false;
            isMouseDragged = false;
            mouseDraggedInteractionOccured = false;
            mouseDraggedInteractionInterrupted = false;
        }



    }
    public void onMouseReleased(){

    }
    @Override
    public void mousePressed(MouseEvent event){
        if(isMouseClicked) return;
        mouseCoords = new Coordinates(event.getX(), event.getY());
        entity = Coordinates.getEntityOnCoordinates(mouseCoords);
    }


    public PeriodicTask playerMovementTask = new PeriodicTask(task,DELAY);

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!mouseCLickQueue.isEmpty()) {
            mouseCLickQueue.add(e);
            return;

        }
        mouseCLickQueue.add(e);
        onMouseClicked(e);

    }
    public void onMouseClicked(MouseEvent e){
        if(isMouseClicked) return;
        isMouseClicked=true;

        if(Bomberman.getMatch().getPlayer()==null|| !Bomberman.getMatch().getPlayer().getAliveState()) {
            return;
        }
        //isMouseClicked = true;
        mouseCoords = new Coordinates(e.getX(), e.getY());
        firstDirectionsFromPlayer = mouseCoords.fromCoordinatesToDirection(Bomberman.getMatch().getPlayer().getCoords());
        entity = Coordinates.getEntityOnCoordinates(mouseCoords);
        playerMovementTask.resume();
    }


    //directions are refreshed and will be replaced in task
    public void onCooldown(){
        for (Direction d: firstDirectionsFromPlayer) {
            Bomberman.getMatch().getControllerManager().onKeyReleased(d.toCommand());
        }
    }
    public void stopPeriodicTask(){
        mouseCLickQueue = new LinkedList<>();
        onPeriodicTaskEnd();
    }
    public void onPeriodicTaskEnd(){

        if(!isMouseClicked) return;
        playerMovementTask.stop();
        onCooldown();
        isMouseClicked = false;

    }
    @Override
    public void mouseDragged(MouseEvent event){
        if(isMouseClicked) return;

        isMouseDragged = true;
        mouseCoords = new Coordinates(event.getX(), event.getY());
        if (entity==null)return;
        entity.mouseInteractions();

    }

    public Entity getEntity(){
        return entity;
    }

}
