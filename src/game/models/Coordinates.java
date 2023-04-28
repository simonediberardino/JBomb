package game.models;

import game.BomberMan;
import game.ui.GameFrame;

import java.awt.*;
import java.time.temporal.ValueRange;
import java.util.*;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public boolean validate() {
        GameFrame gameFrame = BomberMan
                .getInstance()
                .getGameFrame();

        if(gameFrame == null) return false;

        Dimension gamePanelDimensions = BomberMan
                .getInstance()
                .getGameFrame()
                .getGamePanel()
                .getPanelDimensions();

        ValueRange rangeY = ValueRange.of(0, gamePanelDimensions.height - 1);
        ValueRange rangeX = ValueRange.of(0, gamePanelDimensions.height - 1);

        return (rangeY.isValidValue(getY()) && rangeX.isValidValue(getX()));

    }
}
