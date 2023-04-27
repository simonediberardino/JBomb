package game.entity;

import game.models.Coordinates;
import game.models.Direction;
import game.ui.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

public class Bomb extends Block {
    private final int explodeTimer = 5000;
    private final List<Explosion> arrayExplosions = new ArrayList<>();
    static final int size = GamePanel.COMMON_DIVISOR*4;

    @Override
    public Image getImage() {
        final String path = "assets/Bomb/";
        final int refreshRate = 400;
        final int imagesCount = 3;

        String[] images = new String[imagesCount];

        for (int i = 0; i < images.length; i++) {
            images[i] = path + "bomb_" + i + ".png";
        }

        if(System.currentTimeMillis() - lastImageUpdate < refreshRate) {
            return this.image;
        }

        Image img = loadAndSetImage(images[lastImageIndex]);

        lastImageIndex++;
        if(lastImageIndex >= images.length) {
            lastImageIndex = 0;
        }

        return img;
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
        if (isSpawned()) {
            despawn();
            new Explosion(getCoords(), Direction.UP,this);
            new Explosion(getCoords(), Direction.RIGHT,this);
            new Explosion(getCoords(), Direction.DOWN,this);
            new Explosion(getCoords(), Direction.LEFT,this);
            Stream.of(arrayExplosions).forEach(e-> super.setCoords(getCoords()));
        }
    }

    public void trigger() {
        TimerTask explodeTask = new TimerTask() {
            public void run() {
                explode();
            }
        };

        Timer timer = new Timer();
        timer.schedule(explodeTask, explodeTimer);
    }

    @Override
    public int getSize(){
        return size;
    }

    public void addExplosion(Explosion e){
        arrayExplosions.add(e);
    }

    public List<Explosion> getArrayExplosions(){
        return arrayExplosions;
    }



}

