package game.data;

import game.level.Level;

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
    private int lives;

    public PlayerDataObject() {
        this("User",0,0,0,0,0, START_LIVES, 0, 0);
    }

    public PlayerDataObject(String name, int lostGames, int kills, int deaths, int rounds, long points, int lives, int lastLevelId, int lastWorldId) {
        this.name = name;
        this.lostGames = lostGames;
        this.lives = lives;
        this.kills = kills;
        this.deaths = deaths;
        this.rounds = rounds;
        this.points = points;
        this.lastLevelId = lastLevelId;
        this.lastWorldId = lastWorldId;
    }

    String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
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

    void setName(String name) {
        this.name = name;
    }

    void setLostGames(int lostGames) {
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

    void setKills(int kills) {
        this.kills = kills;
    }

    void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    void setPoints(long points) {
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
                ", lives=" + lives +
                '}';
    }

    public void setLastWorldId(int lastWorldId) {
        this.lastWorldId = lastWorldId;
    }

    public void setLastLevelId(int i) {
        this.lastLevelId = i;
    }

}
