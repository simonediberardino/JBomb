package game.entity.enemies.npcs;

import game.utils.Paths;

public class Helicopter extends FlyingEnemy{
    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/heli";
    }

    public String[] getBaseSkins() {
        return new String[]{
                getBasePath() + "/heli_front_0.gif",
                getBasePath() + "/heli_front_1.gif",
                getBasePath() + "/heli_front_2.gif",
        };
    }

    @Override
    public String[] getLeftIcons() {
        return new String[]{
                getBasePath() + "/heli_left_0.gif",
                getBasePath() + "/heli_left_1.gif",
                getBasePath() + "/heli_left_2.gif",
        };
    }

    @Override
    public String[] getBackIcons() {
        return new String[]{
                getBasePath() + "/heli_back_0.gif",
                getBasePath() + "/heli_back_1.gif",
                getBasePath() + "/heli_back_2.gif",
        };
    }

    @Override
    public String[] getRightIcons() {
        return new String[]{
                getBasePath() + "/heli_right_0.gif",
                getBasePath() + "/heli_right_1.gif",
                getBasePath() + "/heli_right_2.gif",
        };
    }
}
