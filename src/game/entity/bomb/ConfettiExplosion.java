package game.entity.bomb;

import game.entity.models.Coordinates;
import game.entity.models.Direction;
import game.entity.models.Explosive;
import game.utils.Paths;

import java.awt.image.BufferedImage;

import static game.utils.Utility.loadImage;

public class ConfettiExplosion extends AbstractExplosion{
    public ConfettiExplosion(Coordinates coordinates, Direction direction, Explosive explosive) {
        super(coordinates, direction, explosive);
    }

    public ConfettiExplosion(Coordinates coordinates, Direction direction, int distanceFromBomb, Explosive explosive) {
        super(coordinates, direction, distanceFromBomb, explosive);
    }

    public ConfettiExplosion(Coordinates coordinates, Direction direction, int distanceFromExplosive, Explosive explosive, boolean canExpand) {
        super(coordinates, direction, distanceFromExplosive, explosive, canExpand);
    }

    @Override
    protected Class<? extends AbstractExplosion> getExplosionClass() {
        return getClass();
    }

    @Override
    protected String getBasePath() {
        return String.format("%s/clown/clown_explosion/", Paths.getEnemiesFolder());
    }

    @Override
    public BufferedImage getImage() {
        if (distanceFromExplosive == 0) {
            return loadImage(String.format("%sclown_explosion_central" + getState() + ".png", getBasePath()));
        }

        String isLast = canExpand ? "" : "_last";
        String imageFileName = "clown_explosion_" + direction.toString().toLowerCase();

        // Load and set the image of the flame.
        String imagePath = String.format("%s%s%s%s.png", getBasePath(), imageFileName, isLast, getState());
        return loadAndSetImage(imagePath);
    }
}
