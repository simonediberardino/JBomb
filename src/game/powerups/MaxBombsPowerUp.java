package game.powerups;

import game.BomberMan;
import game.entity.models.Block;
import game.entity.models.BomberEntity;
import game.entity.models.Character;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.ui.Paths;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

public class MaxBombsPowerUp extends PowerUp {
    /**
     * Constructs an entity with the given coordinates.
     *
     * @param coordinates the coordinates of the entity
     */
    public MaxBombsPowerUp(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public BufferedImage getImage() {
        return loadAndSetImage(Paths.getPowerUpsFolder() + "/fire_up.png");
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public void apply(BomberEntity entity) {
        super.apply(entity);
        entity.increaseMaxBombs();
    }

    @Override
    void cancel(Character character) {

    }
}
