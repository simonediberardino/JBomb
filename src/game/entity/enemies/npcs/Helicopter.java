package game.entity.enemies.npcs;

import game.models.Direction;
import game.utils.Paths;

public class Helicopter extends FlyingEnemy{
    public Helicopter(){
        imagePossibleDirections.remove(Direction.DOWN); imagePossibleDirections.remove(Direction.UP);
    }
    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/heli";
    }

    public String[] getCharacterOrientedImages() {
        return new String[]{
                String.format("%s/heli_%s_0.gif", getBasePath(), imageDirection.toString().toLowerCase()),
                String.format("%s/heli_%s_1.gif", getBasePath(), imageDirection.toString().toLowerCase()),
                String.format("%s/heli_%s_2.gif", getBasePath(), imageDirection.toString().toLowerCase()),
        };
    }

}
