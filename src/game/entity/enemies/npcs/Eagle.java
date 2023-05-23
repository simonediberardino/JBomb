package game.entity.enemies.npcs;

import game.utils.Paths;

public class Eagle extends FlyingEnemy{
    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/eagle";
    }

    public String[] getBaseSkins() {
        setImageDirection();
        return new String[]{
                getBasePath() + "/eagle_"+imageDirection.toString().toLowerCase()+" _0.png",
                getBasePath() + "/eagle_"+ imageDirection.toString().toLowerCase()+ "_1.png",
                getBasePath() + "/eagle_" +imageDirection.toString().toLowerCase()+" _2.png",
        };
    }

}
