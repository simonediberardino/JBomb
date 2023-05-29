package game.entity.bomb;

import game.entity.models.Coordinates;
import game.entity.models.Direction;
import game.entity.models.Explosive;
import game.utils.Paths;

import java.awt.image.BufferedImage;

import static game.utils.Utility.loadImage;

public class FireExplosion extends AbstractExplosion{
    public FireExplosion(Coordinates coordinates, Direction direction, Explosive explosive) {
        super(coordinates, direction, explosive);
    }

    public FireExplosion(Coordinates coordinates, Direction direction, int distanceFromBomb, Explosive explosive) {
        super(coordinates, direction, distanceFromBomb, explosive);
    }

    public FireExplosion(Coordinates coordinates, Direction direction, int distanceFromExplosive, Explosive explosive, boolean canExpand) {
        super(coordinates, direction, distanceFromExplosive, explosive, canExpand);
    }

    @Override
    protected String getBasePath() {
        return String.format("%s/bomb/", Paths.getEntitiesFolder());
    }

    /**
     * Returns the image of the explosion.
     *
     * @return The image of the explosion.
     */
    @Override
    public BufferedImage getImage() {
        if (distanceFromExplosive == 0) {
            return loadImage(String.format("%sflame_central" + getState() + ".png", getBasePath()));
        }

        String isLast = canExpand ? "" : "_last";
        String imageFileName = "flame_" + direction.toString().toLowerCase();

        // Load and set the image of the flame.
        String imagePath = String.format("%s%s%s%s.png", getBasePath(), imageFileName, isLast, getState());
        return loadAndSetImage(imagePath);
    }

    @Override
    protected Class<? extends AbstractExplosion> getExplosionClass() {
        return getClass();
    }
}
