package game.entity;

import game.models.Coordinates;
import game.models.Direction;
import game.ui.GameFrame;
import game.ui.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static game.ui.Utility.loadImage;

public class Bomb extends Block{
    private final int explodeTimer = 5000;

    @Override
    public Image getImage() {
        return loadAndSetImage("assets/Bomb/bomb.png");
    }


    public Bomb(Coordinates coords){
        super(coords);
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    @Override
    public void interact(Entity e) {

    }

    public void explode(){
        new Explosion(getCoords(), Direction.UP);
        new Explosion(getCoords(), Direction.RIGHT);
        new Explosion(getCoords(), Direction.DOWN);
        new Explosion(getCoords(), Direction.LEFT);
    }

    public void trigger() {
        TimerTask explodeTask = new TimerTask() {
            public void run() {
                explode();
                despawn();
            }
        };

        Timer timer = new Timer();
        timer.schedule(explodeTask, explodeTimer);
    }

    public int getSize(){
        return Utility.px(40);
    }


}

