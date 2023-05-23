package game.entity.enemies.npcs;

import game.utils.Paths;

public class Helicopter extends FlyingEnemy{
    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/heli";
    }

    public String[] getBaseSkins() {
        return new String[]{
                getBasePath() + "/heli_" + imageDirection.toString().toLowerCase()+"_" +"0.gif",
                getBasePath() + "/heli_"+imageDirection.toString().toLowerCase() +"_"+"1.gif",
                getBasePath() + "/heli_"+ imageDirection.toString().toLowerCase()+"_"+"2.gif",
        };
    }

}
