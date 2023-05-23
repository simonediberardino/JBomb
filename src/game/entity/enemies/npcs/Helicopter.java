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
                getBasePath() + "/heli_" + imageDirection.toString().toLowerCase()+"_" +"0.gif",
                getBasePath() + "/heli_"+imageDirection.toString().toLowerCase() +"_"+"1.gif",
                getBasePath() + "/heli_"+ imageDirection.toString().toLowerCase()+"_"+"2.gif",
        };
    }

}
