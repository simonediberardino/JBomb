package game.data.data;

import game.domain.level.levels.Level;
import game.utils.file_system.Paths;

import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;

/**
 * Class that stores and retrieves player data from a file using Serialization;
 */
public class DataInputOutput {
    public static int START_LIVES = 3;
    private PlayerDataObject playerDataObject;

    // Private instance of the class
    private static DataInputOutput instance;

    // Private constructor to prevent instantiation from other classes
    private DataInputOutput() {
        playerDataObject = getStoredPlayerData();
    }

    // public method to get the instance of the singleton class
    public static DataInputOutput getInstance() {
        // Lazy initialization: create the instance only if it doesn't exist
        if (instance == null) {
            instance = new DataInputOutput();
        }
        return instance;
    }

    public void retrieveData() {
        playerDataObject = getStoredPlayerData();
        boolean changed = playerDataObject.checkData();

        if (changed) {
            updateStoredPlayerData();
        }
    }

    public void updateStoredPlayerData() {
        updateStoredPlayerData(playerDataObject);
    }

    public void updateStoredPlayerData(PlayerDataObject serObj) {
        try {
            // Creates a data file if still does not exist;
            Files.createDirectories(java.nio.file.Paths.get(Paths.getDataFolder()));

            File dataPath = new File(Paths.getPlayerDataPath());

            Files.createDirectories(dataPath.toPath()); // Create directories if they do not exist

            File dataFile = new File(dataPath, "data");
            dataFile.createNewFile();

            FileOutputStream fileOut = new FileOutputStream(dataFile, false);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public PlayerDataObject getStoredPlayerData() {
        try {
            FileInputStream fileIn = new FileInputStream(Paths.getPlayerDataObjectPath());
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            PlayerDataObject obj = (PlayerDataObject) objectIn.readObject();

            objectIn.close();
            return obj;
        } catch (Exception ex) {
            return new PlayerDataObject();
        }
    }

    public PlayerDataObject getPlayerDataObject() {
        return playerDataObject;
    }

    public void setUsername(String name) {
        playerDataObject.name = name.trim();
        updateStoredPlayerData();
    }

    public String getUsername() {
        return playerDataObject.name;
    }

    public void increaseKills() {
        playerDataObject.kills = playerDataObject.kills + 1;
        updateStoredPlayerData();
    }

    public void resetExplosionLength() {
        setExplosionLength(1);
    }

    public void resetMaxBombs() {
        setObtainedBombs(1);
    }

    public void setExplosionLength(int newExplosionLength) {
        playerDataObject.explosionLength = newExplosionLength;
        updateStoredPlayerData();
    }

    public int getExplosionLength() {
        return playerDataObject.explosionLength;
    }

    public int getObtainedBombs() {
        return playerDataObject.obtainedBombs;
    }

    public void setObtainedBombs(int newMaxBombs) {
        newMaxBombs = Math.max(0, newMaxBombs);
        playerDataObject.obtainedBombs = newMaxBombs;
        updateStoredPlayerData();
    }

    public void increaseObtainedBombs() {
        setObtainedBombs(getObtainedBombs() + 1);
    }

    public void increaseDeaths() {
        playerDataObject.deaths = playerDataObject.deaths + 1;
        updateStoredPlayerData();
    }

    public int getLives() {
        return playerDataObject.lives;
    }

    public void resetLives() {
        setLives(START_LIVES);
    }

    public void increaseLives() {
        int nextLives = playerDataObject.lives + 1;
        setLives(nextLives);
    }

    private void setLives(int nextLives) {
        playerDataObject.lives = nextLives;
        updateStoredPlayerData();
    }

    public void decreaseLives() {
        int newLives = Math.max(playerDataObject.lives - 1, 0);
        if (newLives == 0) {
            increaseLost();
            resetLevel();
            resetScore();
        }

        setLives(newLives);
    }

    public void resetLivesIfNecessary() {
        if (getLives() <= 0)
            setLives(START_LIVES);
    }

    public void resetLevel() {
        playerDataObject.lastWorldId = 1;
        playerDataObject.lastLevelId = 1;
        resetExplosionLength();
        resetMaxBombs();
        updateStoredPlayerData();
    }

    public long getScore() {
        return playerDataObject.points;
    }

    public void resetScore() {
        setScore(0);
    }

    public void decreaseScore(int score) {
        setScore((int) (playerDataObject.points - score));
    }

    public void increaseScore(int score) {
        setScore((int) (playerDataObject.points + score));
    }

    private void setScore(int score) {
        score = Math.max(0, score);
        playerDataObject.points = score;
        updateStoredPlayerData();
    }

    public void setName(String name) {
        playerDataObject.name = name;
        updateStoredPlayerData();
    }

    public void setLastLevel(Level level) {
        if (playerDataObject.lastWorldId > level.getInfo().getWorldId()) return;
        if (playerDataObject.lastWorldId == level.getInfo().getWorldId() && playerDataObject.lastLevelId >= level.getInfo().getLevelId())
            return;
        playerDataObject.setLastLevel(level);
        updateStoredPlayerData();
    }

    public void increaseLost() {
        playerDataObject.lostGames = playerDataObject.lostGames + 1;
        updateStoredPlayerData();
    }

    public void increaseRounds() {
        playerDataObject.rounds = playerDataObject.rounds + 1;
        updateStoredPlayerData();
    }

    public void setSkin(String skin) {
        playerDataObject.skin = skin;
        updateStoredPlayerData();
    }

    public String getSkin() {
        return playerDataObject.skin;
    }

    public String getLeftKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.leftKey);
    }

    public String getForwardKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.forwardKey);
    }

    public String getBackKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.backKey);
    }

    public String getRightKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.rightKey);
    }

    public String getBombKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.bombKey);
    }

    public String getInteractKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.interactKey);
    }

    public int getLeftKey() {
        return playerDataObject.leftKey;
    }

    public int getForwardKey() {
        return playerDataObject.forwardKey;
    }

    public int getBackKey() {
        return playerDataObject.backKey;
    }

    public int getRightKey() {
        return playerDataObject.rightKey;
    }

    public int getBombKey() {
        return playerDataObject.bombKey;
    }

    public int getInteractKey() {
        return playerDataObject.interactKey;
    }

    public void resetKeys() {
        playerDataObject.resetKeys();
    }

    public void setForwardKey(int forwardKey) {
        playerDataObject.forwardKey = forwardKey;
        updateStoredPlayerData();
    }

    public void setBackKey(int backKey) {
        playerDataObject.backKey = backKey;
        updateStoredPlayerData();
    }

    public void setLeftKey(int leftKey) {
        playerDataObject.leftKey = leftKey;
        updateStoredPlayerData();
    }

    public void setRightKey(int rightKey) {
        playerDataObject.rightKey = rightKey;
        updateStoredPlayerData();
    }

    public void setBombKey(int bombKey) {
        playerDataObject.bombKey = bombKey;
        updateStoredPlayerData();
    }

    public void setInteractKey(int interactKey) {
        playerDataObject.interactKey = interactKey;
        updateStoredPlayerData();
    }

    public void setVolume(int volume) {
        playerDataObject.volume = volume;
        updateStoredPlayerData();
    }

    public int getVolume() {
        return playerDataObject.volume;
    }

    public int getLastLevelId() {
        return playerDataObject.lastLevelId;
    }

    public int getLastWorldId() {
        return playerDataObject.lastWorldId;
    }

    public int getDeaths() {
        return playerDataObject.deaths;
    }

    public int getKills() {
        return playerDataObject.kills;
    }

    public int getRounds() {
        return playerDataObject.rounds;
    }

    public int getLostGames() {
        return playerDataObject.lostGames;
    }
}