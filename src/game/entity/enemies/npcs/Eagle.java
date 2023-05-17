package game.entity.enemies.npcs;

import game.utils.Paths;

public class Eagle extends FlyingEnemy{
    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/eagle";
    }

    public String[] getBaseSkins() {
        return new String[]{
                getBasePath() + "/eagle_front_0.png",
                getBasePath() + "/eagle_front_1.png",
                getBasePath() + "/eagle_front_2.png",
        };
    }

    @Override
    public String[] getLeftIcons() {
        return new String[]{
                getBasePath() + "/eagle_left_0.png",
                getBasePath() + "/eagle_left_1.png",
                getBasePath() + "/eagle_left_2.png",
        };
    }

    @Override
    public String[] getBackIcons() {
        return new String[]{
                getBasePath() + "/eagle_back_0.png",
                getBasePath() + "/eagle_back_1.png",
                getBasePath() + "/eagle_back_2.png",
        };
    }

    @Override
    public String[] getRightIcons() {
        return new String[]{
                getBasePath() + "/eagle_right_0.png",
                getBasePath() + "/eagle_right_1.png",
                getBasePath() + "/eagle_right_2.png",
        };
    }
}
