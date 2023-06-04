package game.entity.enemies.npcs;

import game.entity.enemies.boss.clown.Clown;
import game.entity.models.Coordinates;
import game.entity.models.Direction;
import game.entity.models.EnhancedDirection;
import game.sound.AudioManager;
import game.sound.SoundModel;
import game.utils.Paths;

public class ClownNose extends Orb {
    public ClownNose(Coordinates coordinates, EnhancedDirection enhancedDirection) {
        super(coordinates,enhancedDirection);
    }
    public ClownNose(Coordinates coordinates, Direction direction) {
        super(coordinates,direction);
    }

    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/clown/clown_orb.png";
    }

    /**
     Returns an array of the skin of the Orb.
     @return an array of the skin of the Orb
     */
    @Override
    //
    public String[] getCharacterOrientedImages() {
        return new String[]{
                getBasePath()
        };
    }

    @Override
    protected void onDespawn() {
        super.onDespawn();
        AudioManager.getInstance().play(SoundModel.CLOWN_NOSE_DEATH);
    }
    /**

     Returns the size of the Orb.
     @return the size of the Orb
     */
}
