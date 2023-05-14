package game.data;

import game.level.Level;

import java.io.Serializable;

public class PlayerDataObject implements Serializable {
    private String name;
    private int victories;
    private int lostGames;
    private int kills;
    private int deaths;
    private int rounds;
    private long points;
    private int lastLevelId;
    private int lastWorldId;

    public PlayerDataObject(){
        this("User",0,0,0,0,0,0,0, 0);
    }

    public PlayerDataObject(String name, int victories, int lostGames, int kills, int deaths, int rounds, long points, int lastLevelId, int lastWorldId) {
        this.name = name;
        this.victories = victories;
        this.lostGames = lostGames;

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

    public int getVictories() {
        return victories;
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

    void setVictories(int victories) {
        this.victories = victories;
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
                ", victories=" + victories +
                ", lostGames=" + lostGames +
                ", lastWorldId=" + lastWorldId +
                ", lastLevelId=" + lastLevelId +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", points=" + points +
                '}';
    }
}
