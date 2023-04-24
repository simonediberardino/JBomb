package game.entity;

import game.BomberMan;
import game.models.Direction;
import game.ui.GridImage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Character extends InteractiveEntities {
    public static final int SIZE = 3;
    private int currentPositionIconIndex = 0;
    private boolean isAlive = true;
    private Direction lastDirection = Direction.DOWN;

    public abstract String[] getFrontIcons();
    public abstract String[] getLeftIcons();
    public abstract String[] getBackIcons();
    public abstract String[] getRightIcons();

    public Character(Coordinates coordinates) {
        super(coordinates);
    }
    
    public void setAliveState(boolean x){
        isAlive = x;
    }
    
    public boolean getAliveState(){
        return isAlive;
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public Icon[] getIcon(){
        String[] frontIcons = getFrontIcons();
        String[] leftIcons = getLeftIcons();
        String[] backIcons = getBackIcons();
        String[] rightIcons = getRightIcons();

        if(lastDirection == null)
            return new GridImage(frontIcons[0], getSize()).generate();

        String[] icons;
        switch (lastDirection) {
            case LEFT: icons = leftIcons; break;
            case RIGHT: icons = rightIcons; break;
            case UP: icons = backIcons; break;
            default: icons = frontIcons; break;
        }

        if(currentPositionIconIndex < 0 || currentPositionIconIndex >= icons.length)
            currentPositionIconIndex = 0;

        String icon = icons[currentPositionIconIndex];
        return new GridImage(icon, getSize()).generate();
    }


    private void updateLastDirection(Direction d){
        if(lastDirection != d){
            currentPositionIconIndex = 0;
        }else {
            currentPositionIconIndex++;
        }
        lastDirection = d;
    }

    public void move(Direction d) {
        updateLastDirection(d);
        moveOrInteract(d);
    }
    public Direction getLastDirection(){
        return lastDirection;
    }


}
