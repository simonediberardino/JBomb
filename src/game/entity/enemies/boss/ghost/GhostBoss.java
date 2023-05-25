package game.entity.enemies.boss.ghost;

import game.entity.enemies.GhostEnemy;
import game.entity.enemies.boss.Boss;
import game.models.Coordinates;
import game.utils.Utility;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class GhostBoss extends Boss {
    private int ACTION_CHANCE = 1;
    private final int MAX_GHOST_ENEMY_SPAWNED = 5;

    public GhostBoss(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected Map<Integer, Integer> healthStatusMap() {
        return null;
    }

    @Override
    protected void updateRageStatus(int status) {

    }

    @Override
    public String[] getCharacterOrientedImages() {
        return new String[]{"assets/entities/enemies/ghost_boss/ghost.png"};
    }
    private void disappear(){
        if (image==null)return;
        BufferedImage lastImage = image;
        new Thread(() -> {
            image = null;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            image = lastImage;
        }

        ).start();
    }

    @Override
    public void doUpdate(boolean gamestate) {
        Utility.runPercentage(ACTION_CHANCE, new Runnable() {
            @Override
            public void run() {
                disappear();
            }
        });
        Utility.runPercentage(ACTION_CHANCE, new Runnable() {
            @Override
            public void run() {
                int enemyCount = (int) (Math.random()*MAX_GHOST_ENEMY_SPAWNED);     //spawn ghost enemies
                for(int i = 0; i<= enemyCount;i++){
                    new GhostEnemy().spawnAtRandomCoordinates();
                }
            }
        });

    }
}
