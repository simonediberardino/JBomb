package game.powerups;

import game.entity.bomb.Bomb;
import game.entity.models.BomberEntity;
import game.models.Coordinates;
import game.utils.Paths;

import java.awt.image.BufferedImage;

public class TransparentBombsPowerUp extends PowerUp {
    public TransparentBombsPowerUp(Coordinates coordinates) {
        super(coordinates);
    }

    //TODO
    @Override
    public BufferedImage getImage() {
        return loadAndSetImage(Paths.getPowerUpsFolder() + "/fire_up.png");
    }


    @Override
    public int getDuration() {
        return DEFAULT_DURATION_SEC;
    }

    @Override
    protected void doApply(BomberEntity entity) {
        entity.getWhiteListObstacles().add(Bomb.class);
    }

    @Override
    protected void cancel(BomberEntity entity) {
        entity.getWhiteListObstacles().remove(Bomb.class);
    }
}
