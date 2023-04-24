package game.entity;

import game.models.Coordinates;
import game.models.Direction;
import game.ui.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public abstract class Character extends InteractiveEntities {
    public final static int STEP_SIZE = Utility.px(10);
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

    public void setAliveState(boolean x) {
        isAlive = x;
    }

    public boolean getAliveState() {
        return isAlive;
    }

    @Override
    public int getSize() {
        return Utility.px(50);
    }

    @Override
    public Image getImage() {
        String[] frontIcons = getFrontIcons();
        String[] leftIcons = getLeftIcons();
        String[] backIcons = getBackIcons();
        String[] rightIcons = getRightIcons();

        if (lastDirection == null) {
            try {
                return ImageIO.read(new File(frontIcons[0]));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        String[] icons;
        switch (lastDirection) {
            case LEFT:
                icons = leftIcons;
                break;
            case RIGHT:
                icons = rightIcons;
                break;
            case UP:
                icons = backIcons;
                break;
            default:
                icons = frontIcons;
                break;
        }

        if (currentPositionIconIndex < 0 || currentPositionIconIndex >= icons.length)
            currentPositionIconIndex = 0;

        String icon = icons[currentPositionIconIndex];
        try {
            return ImageIO.read(new File(icon));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void updateLastDirection(Direction d) {
        if (lastDirection != d) {
            currentPositionIconIndex = 0;
        } else {
            currentPositionIconIndex++;
        }
        lastDirection = d;
    }

    public void move(Direction d) {
        updateLastDirection(d);
        moveOrInteract(d);
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    @Override
    public void despawn() {
        setAliveState(false);
        super.despawn();
    }
}
