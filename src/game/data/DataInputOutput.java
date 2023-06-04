package game.data;

import game.Bomberman;
import game.level.Level;
import game.level.world1.World1Level1;
import game.utils.Paths;

import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;

import static game.level.Level.ID_TO_FIRST_LEVEL_MAP;

/**
 * Class that stores and retrieves player data from a file using Serialization;
 */
public class DataInputOutput {
    public static int START_LIVES = 3;
    private static PlayerDataObject playerDataObject;

    public static void retrieveData() {
        playerDataObject = getStoredPlayerData();
    }

    public static void updateStoredPlayerData() {
        updateStoredPlayerData(playerDataObject);
    }

    public static void updateStoredPlayerData(PlayerDataObject serObj) {
        try {
            // Creates a data file if still does not exist;
            Files.createDirectories(java.nio.file.Paths.get(Paths.getDataFolder()));

            File dataFile = new File(Paths.getPlayerDataPath());

            dataFile.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(dataFile, false);
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

    public static void setUsername(String name){
        playerDataObject.setName(name.trim());
        updateStoredPlayerData();
    }

    public static String getUsername() {
        return playerDataObject.getName();
    }

    public static void increaseKills(){
        playerDataObject.setKills(playerDataObject.getKills()+1);
        updateStoredPlayerData();
    }

    public static void increaseExplosionLength(){
        setExplosionLength(playerDataObject.getExplosionLength()+1);
    }

    public static void resetExplosionLength(){
        setExplosionLength(1);
    }

    public static void resetMaxBombs(){
        setObtainedBombs(1);
    }

    public static void setExplosionLength(int newExplosionLength){
        playerDataObject.setExplosionLength(newExplosionLength);
        updateStoredPlayerData();
    }

    public static int getExplosionLength(){
        return playerDataObject.getExplosionLength();
    }

    public static int getObtainedBombs(){
        return playerDataObject.getObtainedBombs();
    }

    public static void setObtainedBombs(int newMaxBombs){
        newMaxBombs = Math.max(0, newMaxBombs);
        playerDataObject.setObtainedBombs(newMaxBombs);
        updateStoredPlayerData();
    }

    public static void increaseObtainedBombs(){
        setObtainedBombs(getObtainedBombs()+1);
    }

    public static void increaseDeaths(){
        playerDataObject.setDeaths(playerDataObject.getDeaths()+1);
        updateStoredPlayerData();
    }

    public static int getLives(){
        return playerDataObject.getLives();
    }

    public static void resetLives(){
        setLives(START_LIVES);
    }

    public static void increaseLives(){
        int nextLives = playerDataObject.getLives()+1;
        setLives(nextLives);
    }

    private static void setLives(int nextLives) {
        Bomberman.getMatch().getInventoryElementControllerLives().setNumItems(nextLives);
        DataInputOutput.getPlayerDataObject().setLives(nextLives);
        playerDataObject.setLives(nextLives);
        updateStoredPlayerData();
    }

    public static void decreaseLives() {
        int newLives = Math.max(playerDataObject.getLives() - 1, 0);
        if(newLives <= 0) {
            increaseLost();
            resetLevel();
            resetScore();
        }

        setLives(newLives);
    }

    public static void resetLivesIfNecessary() {
        if(getLives() <= 0)
            setLives(START_LIVES);
    }

    public static void resetLevel() {
        playerDataObject.setLastWorldId(1);
        playerDataObject.setLastLevelId(1);
        resetExplosionLength();
        resetMaxBombs();
        updateStoredPlayerData();
    }

    public static long getScore() {
        return playerDataObject.getPoints();
    }

    public static void resetScore() {
        setScore(0);
    }

    public static void decreaseScore(int score){
        setScore((int) (playerDataObject.getPoints() - score));
    }

    public static void increaseScore(int score){
        setScore((int) (playerDataObject.getPoints() + score));
    }

    private static void setScore(int score) {
        score = Math.max(0, score);
        playerDataObject.setPoints(score);
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

    public static String getLeftKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.getLeftKey());
    }

    public static String getForwardKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.getForwardKey());
    }

    public static String getBackKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.getBackKey());
    }

    public static String getRightKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.getRightKey());
    }

    public static String getBombKeyChar() {
        return KeyEvent.getKeyText(playerDataObject.getBombKey());
    }


    public static void setForwardKey(int forwardKey) {
        playerDataObject.setForwardKey(forwardKey);
        updateStoredPlayerData();
    }

    public static void setBackKey(int backKey) {
        playerDataObject.setBackKey(backKey);
        updateStoredPlayerData();
    }

    public static void setLeftKey(int leftKey) {
        playerDataObject.setLeftKey(leftKey);
        updateStoredPlayerData();
    }

    public static void setRightKey(int rightKey) {
        playerDataObject.setRightKey(rightKey);
        updateStoredPlayerData();
    }

    public static void setBombKey(int bombKey) {
        playerDataObject.setBombKey(bombKey);
        updateStoredPlayerData();
    }
}
