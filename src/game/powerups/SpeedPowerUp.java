package game.powerups;

import game.entity.models.Character;
import game.entity.models.Entity;
import game.models.Coordinates;

import java.awt.image.BufferedImage;

public class SpeedPowerUp extends PowerUp{
    /**
     * Constructs an entity with the given coordinates.
     *
     * @param coordinates the coordinates of the entity
     */
    public SpeedPowerUp(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected String getBasePath() {
        return null;
    }

    @Override
    public BufferedImage getImage() {
        return null;
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    void cancel(Character character) {

    }
}
