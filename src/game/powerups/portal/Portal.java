package game.powerups.portal;

import game.entity.models.BomberEntity;
import game.models.Coordinates;
import game.powerups.PowerUp;
import game.utils.Paths;

import java.awt.image.BufferedImage;

public abstract class Portal extends PowerUp {
    public Portal(Coordinates coordinates){
        super(coordinates);
    }
}
