package game.data;

import java.io.Serializable;

public class PlayerDataObject implements Serializable {
    private String name;
    private int victories;
    private int lostGames;
    private int lastWorldId;
    private int lastLevelId;
    private int kills;
    private int deaths;
    private int rounds;
    private long points;

    public PlayerDataObject(){
        this("User", 0,0,0,0,0,0,0, 0);
    }

    public PlayerDataObject(String name, int victories, int lostGames, int lastWorldId, int lastLevelId, int kills, int deaths, int rounds, long points) {
        this.name = name;
        this.victories = victories;
        this.lostGames = lostGames;
        this.lastWorldId = lastWorldId;
        this.lastLevelId = lastLevelId;
        this.kills = kills;
        this.deaths = deaths;
        this.rounds = rounds;
        this.points = points;
    }

    String getName() {
        return name;
    }

    int getVictories() {
        return victories;
    }

    int getLostGames() {
        return lostGames;
    }

    int getLastWorldId() {
        return lastWorldId;
    }

    int getLastLevelId() {
        return lastLevelId;
    }

    int getKills() {
        return kills;
    }

    int getDeaths() {
        return deaths;
    }

    long getPoints() {
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

    void setLastWorldId(int lastWorldId) {
        this.lastWorldId = lastWorldId;
    }

    void setLastLevelId(int lastLevelId) {
        this.lastLevelId = lastLevelId;
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
