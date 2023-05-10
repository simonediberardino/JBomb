package game.powerups;

import game.BomberManMatch;
import game.entity.models.BomberEntity;
import game.entity.models.Transparent;
import game.models.Coordinates;
import game.utils.Paths;

import java.awt.image.BufferedImage;

public class EndgamePowerUp extends PowerUp {
    /**
     * Constructs a PowerUp entity with the specified coordinates.
     *
     * @param coordinates the coordinates of the PowerUp entity
     */
    public EndgamePowerUp(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public BufferedImage getImage() {
        return loadAndSetImage(Paths.getPowerUpsFolder() + "/end_game.gif");
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    protected void doApply(BomberEntity entity) {
        BomberManMatch.getInstance().nextLevel();
    }

    @Override
    protected void cancel(BomberEntity entity) {}
}
