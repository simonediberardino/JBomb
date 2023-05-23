package game.data;

import game.level.Level;
import game.level.world1.World1Level1;
import game.utils.Paths;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

import static game.level.Level.ID_TO_FIRST_LEVEL_MAP;

public class DataInputOutput {
    public static int START_LIVES = 3;
    private static PlayerDataObject playerDataObject;

    public static void retrieveData() {
        playerDataObject = getStoredPlayerData();
        System.out.println(playerDataObject);
    }

    public static void updateStoredPlayerData() {
        updateStoredPlayerData(playerDataObject);
    }

    public static void updateStoredPlayerData(PlayerDataObject serObj) {
        try {
            FileOutputStream fileOut = new FileOutputStream(Paths.getPlayerDataPath());
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static PlayerDataObject getStoredPlayerData() {
        try {
            FileInputStream fileIn = new FileInputStream(Paths.getPlayerDataPath());
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            PlayerDataObject obj = (PlayerDataObject) objectIn.readObject();

            objectIn.close();
            return obj;
        } catch (Exception ex) {
            return new PlayerDataObject();
        }
    }

    public static PlayerDataObject getPlayerDataObject() {
        return playerDataObject;
    }

    public static void increaseKills(){
        playerDataObject.setKills(playerDataObject.getKills()+1);
        updateStoredPlayerData();
    }

    public static void increaseDeaths(){
        playerDataObject.setDeaths(playerDataObject.getDeaths()+1);
        updateStoredPlayerData();
    }

    public static int getLives(){
        return playerDataObject.getLives();
    }

    public static void resetLives(){
        playerDataObject.setLives(START_LIVES);
        updateStoredPlayerData();
    }

    public static void increaseLives(){
        playerDataObject.setLives(playerDataObject.getLives()+1);
        updateStoredPlayerData();
    }

    public static void decreaseLives() {
        int newLives = Math.max(playerDataObject.getLives() - 1, 0);
        if(newLives <= 0) {
            increaseLost();
            resetLevel();
        }

        playerDataObject.setLives(newLives);
        updateStoredPlayerData();
    }

    public static void resetLivesIfNecessary() {
        if(getLives() <= 0)
            playerDataObject.setLives(START_LIVES);
    }

    public static void resetLevel() {
        playerDataObject.setLastWorldId(playerDataObject.getLastWorldId());
        playerDataObject.setLastLevelId(1);
        updateStoredPlayerData();
    }

    public static long getScore() {
        return playerDataObject.getPoints();
    }

    public static void increaseScore(int score){
        playerDataObject.setPoints(playerDataObject.getPoints() + score);
        updateStoredPlayerData();
    }

    public static void setName(String name) {
        playerDataObject.setName(name);
        updateStoredPlayerData();
    }

    public static void setLastLevel(Level level) {
        if(playerDataObject.getLastWorldId() > level.getWorldId()) return;
        if(playerDataObject.getLastWorldId() == level.getWorldId() && playerDataObject.getLastLevelId() >= level.getLevelId()) return;
        playerDataObject.setLastLevel(level);
        updateStoredPlayerData();
    }

    public static void increaseLost(){
        playerDataObject.setLostGames(playerDataObject.getLostGames() + 1);
        updateStoredPlayerData();
    }

    public static void increaseRounds(){
        playerDataObject.setRounds(playerDataObject.getRounds() + 1);
        updateStoredPlayerData();
    }

    public static Level getLastLevelInstance() {
        int worldId = playerDataObject.getLastWorldId();
        int levelId = playerDataObject.getLastLevelId();

        Optional<Class<? extends Level>> lastLevel = Level.ID_TO_LEVEL.entrySet().stream().filter(e -> e.getKey()[0] == worldId && e.getKey()[1] == levelId).findFirst().map(Map.Entry::getValue);

        Class<? extends Level> levelClass = lastLevel.isPresent() ? lastLevel.get() : ID_TO_FIRST_LEVEL_MAP.get(1);

        try {
            return levelClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new World1Level1();
    }
}
