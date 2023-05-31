package game.data;

import game.level.Level;
import game.localization.Localization;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import static game.data.DataInputOutput.START_LIVES;

public class PlayerDataObject implements Serializable {
    private String name;
    private int lostGames;
    private int kills;
    private int deaths;
    private int rounds;
    private long points;
    private int lastLevelId;
    private int lastWorldId;
    private int explosionLength;
    private int maxBombs;
    private int lives;
    private int forwardKey;
    private int backKey;
    private int leftKey;
    private int rightKey;
    private int bombKey;

    public PlayerDataObject() {
        this(
                Localization.get(Localization.PLAYER),
                0,
                0,
                0,
                0,
                0,
                START_LIVES,
                0,
                1,
                1,
                0,
                0,
                0,
                0,
                0,
                0
        );

        resetKeys();
    }

    public PlayerDataObject(String name, int lostGames, int kills, int deaths, int rounds, long points, int lastLevelId, int lastWorldId, int explosionLength,int maxBombs, int lives, int forwardKey, int backKey, int leftKey, int rightKey, int bombKey) {
        this.name = name;
        this.lostGames = lostGames;
        this.kills = kills;
        this.deaths = deaths;
        this.rounds = rounds;
        this.points = points;
        this.lastLevelId = lastLevelId;
        this.lastWorldId = lastWorldId;
        this.explosionLength = explosionLength;
        this.maxBombs = maxBombs;
        this.lives = lives;
        this.forwardKey = forwardKey;
        this.backKey = backKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.bombKey = bombKey;
    }

    public void resetKeys() {
        this.forwardKey = KeyEvent.VK_W;
        this.backKey = KeyEvent.VK_S;
        this.leftKey = KeyEvent.VK_A;
        this.rightKey = KeyEvent.VK_D;
        this.bombKey = KeyEvent.VK_SPACE;
    }

    public int getForwardKey() {
        return forwardKey;
    }

    public void setForwardKey(int forwardKey) {
        this.forwardKey = forwardKey;
    }

    public int getBackKey() {
        return backKey;
    }

    public void setBackKey(int backKey) {
        this.backKey = backKey;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public int getRightKey() {
        return rightKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    public int getBombKey() {
        return bombKey;
    }

    public void setBombKey(int bombKey) {
        this.bombKey = bombKey;
    }

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    public void setExplosionLength(int explosionLength){this.explosionLength = explosionLength;}
    public int getExplosionLength() {return explosionLength;}
    public void setMaxBombs(int maxBombs){
        this.maxBombs = maxBombs;
    }
    public int getMaxBombs(){
        return maxBombs;
    }
    public int getLostGames() {
        return lostGames;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public long getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLostGames(int lostGames) {
        this.lostGames = lostGames;
    }

    public void setLastLevel(Level lastLevel) {
        this.lastLevelId = lastLevel.getLevelId();
        this.lastWorldId = lastLevel.getWorldId();
    }

    public int getLastLevelId() {
        return lastLevelId;
    }

    public int getLastWorldId() {
        return lastWorldId;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    @Override
    public String toString() {
        return "PlayerDataObject{" +
                "name='" + name + '\'' +
                ", lostGames=" + lostGames +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", rounds=" + rounds +
                ", points=" + points +
                ", lastLevelId=" + lastLevelId +
                ", lastWorldId=" + lastWorldId +
                ", explosionLength=" + explosionLength +
                ", lives=" + lives +
                ", forwardKey=" + KeyEvent.getKeyText(forwardKey) +
                ", backKey=" + KeyEvent.getKeyText(backKey) +
                ", leftKey=" + KeyEvent.getKeyText(leftKey) +
                ", rightKey=" + KeyEvent.getKeyText(rightKey) +
                ", bombKey=" + KeyEvent.getKeyText(bombKey) +
                '}';
    }

    public void setLastWorldId(int lastWorldId) {
        this.lastWorldId = lastWorldId;
    }

    public void setLastLevelId(int i) {
        this.lastLevelId = i;
    }
}
