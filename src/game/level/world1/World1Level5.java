package game.level.world1;

import game.entity.enemies.boss.Boss;
import game.entity.enemies.boss.clown.Clown;
import game.entity.enemies.npcs.FlyingEnemy;
import game.entity.enemies.npcs.YellowBall;
import game.entity.models.Enemy;
import game.level.Level;
import game.level.WorldSelectorLevel;
import game.level.world2.World2Level;

public class World1Level5 extends World1Level{
    @Override
    public int getLevelId() {
        return 5;
    }

    @Override
    public Boss getBoss() {
        return new Clown();
    }

    @Override
    public int startEnemiesCount() {
        return 7;
    }

    @Override
    public Class<? extends Enemy>[] availableEnemies() {
        return new Class[]{
                YellowBall.class,
                FlyingEnemy.class
        };
    }

    @Override
    public boolean isLastLevelOfWorld(){
        return true;
    }

    @Override
    public Class<? extends Level> getNextLevel() {
        return WorldSelectorLevel.class;
    }
}