package game.entity;

import game.entity.models.Block;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.models.Direction;
import game.ui.GamePanel;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Block {
    public static final long PLACE_INTERVAL = 1000;
    public static final int size = GamePanel.COMMON_DIVISOR * 4;
    private final int explodeTimer = 5000;
    private Runnable onExplodeCallback;
    private static final int imageRefreshRate = 250;

    @Override
    public BufferedImage getImage() {
        final String path = "assets/Bomb/";
        final int imagesCount = 3;

        String[] images = new String[imagesCount];

        for (int i = 0; i < images.length; i++) {
            images[i] = String.format("%sbomb_%d.png", path, i);
        }

        if(System.currentTimeMillis() - lastImageUpdate < imageRefreshRate) {
            return this.image;
        }

        BufferedImage img = loadAndSetImage(images[lastImageIndex]);

        lastImageIndex++;
        if(lastImageIndex >= images.length) {
            lastImageIndex = 0;
        }

        return img;
    }


    public Bomb(Coordinates coords){
        super(coords);
    }

    @Override
    protected void onSpawn() {

    }

    @Override
    protected void onDespawn() {

    }

    public void setOnExplodeListener(Runnable runnable){
        onExplodeCallback = runnable;
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
        if (!isSpawned()) {
            return;
        }

        despawn();
        new Explosion(getCoords(), Direction.UP,this);
        new Explosion(getCoords(), Direction.RIGHT,this);
        new Explosion(getCoords(), Direction.DOWN,this);
        new Explosion(getCoords(), Direction.LEFT,this);

        if(onExplodeCallback != null) onExplodeCallback.run();
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
}

