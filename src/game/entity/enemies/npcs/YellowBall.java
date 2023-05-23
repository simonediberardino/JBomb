package game.entity.enemies.npcs;

import game.models.Coordinates;
import game.models.Direction;
import game.ui.panels.game.PitchPanel;
import game.utils.Paths;

public class YellowBall extends IntelligentEnemy {
    public YellowBall() {
        super();
    }

    public YellowBall(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected String getBasePath() {
        return Paths.getEnemiesFolder() + "/yellow_ball/yellow_ball";
    }

    @Override
    public String[] getBaseSkins() {
        setImageDirection();
        return new String[]{
                Paths.getEnemiesFolder() + "/yellow_ball/yellow_ball_" + imageDirection.toString().toLowerCase() +"_"+ 0 + ".png",
                Paths.getEnemiesFolder() + "/yellow_ball/yellow_ball_" + imageDirection.toString().toLowerCase()+"_" + 1 + ".png",
                Paths.getEnemiesFolder() + "/yellow_ball/yellow_ball_" + imageDirection.toString().toLowerCase()+"_" + 2 + ".png",
                Paths.getEnemiesFolder() + "/yellow_ball/yellow_ball_" + imageDirection.toString().toLowerCase() +"_"+ 3 + ".png"
        };



    }



    @Override
    public int getSize() {
        return PitchPanel.COMMON_DIVISOR * 2;
    }
}
