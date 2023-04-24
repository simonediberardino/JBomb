package game.ui;

import game.BomberMan;
import game.entity.Entity;
import game.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GamePanel extends JPanel implements Observer {
    public static Dimension DEFAULT_DIMENSION = new Dimension(770, 630);
    public GamePanel() {
        int convertedHeight = Utility.px((int) DEFAULT_DIMENSION.getHeight());
        int convertedWidth = Utility.px((int) DEFAULT_DIMENSION.getWidth());

        setPreferredSize(new Dimension(convertedWidth, convertedHeight));
        setMaximumSize(new Dimension(convertedWidth, convertedHeight));
        setMinimumSize(new Dimension(convertedWidth, convertedHeight));

        BomberMan.getInstance().getGameTickerObservable().deleteObservers();
        BomberMan.getInstance().getGameTickerObservable().addObserver(this);

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Set<Entity> set =  BomberMan.getInstance().getEntities();

        Entity[] fromSetToArray = set.toArray(new Entity[set.size()]);
        for(Entity entity : fromSetToArray){
            drawEntity(g2d, entity);
        }
    }

    private void drawEntity(Graphics2D g2d, Entity e){
        g2d.drawImage(e.getImage(), e.getCoords().getX(), e.getCoords().getY(), e.getSize(), e.getSize(), this);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
