package game.controller;

import game.Bomberman;
import game.entity.bomb.Bomb;
import game.entity.models.Entity;
import game.models.Coordinates;

import java.awt.*;
import java.awt.event.*;

public class MouseControllerManager implements MouseListener, MouseWheelListener, MouseMotionListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!Bomberman.getMatch().getPlayer().getAliveState()) return;
        Coordinates mouseCoords = new Coordinates(e.getX(), e.getY());
        Entity entity = Coordinates.getEntityOnCoordinates(mouseCoords);

        if(entity == null) return;
        if (!Bomberman.getMatch().getPlayer().getListClassInteractWithMouse().contains(entity.getClass())) return;

        entity.onMouseClick();
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    @Override public void mouseMoved(MouseEvent e) { }
    @Override public void mouseWheelMoved(MouseWheelEvent e) { }
}
