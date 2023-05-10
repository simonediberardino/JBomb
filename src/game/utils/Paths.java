package game.utils;

import game.BomberManMatch;
import game.level.Level;

public class Paths {
    public static String getAssetsFolder(){
        return "assets";
    }

    public static String getEntitiesFolder(){
        return getAssetsFolder() + "/entities";
    }

    public static String getEnemiesFolder(){
        return getEntitiesFolder() + "/enemies";
    }

    public static String getWorldsFolder(){
        return getAssetsFolder() + "/worlds";
    }

    public static String getCurrentLevelFolder(){
        Level level = BomberManMatch.getInstance().getCurrentLevel();
        return String.format("%s/%d/level/%d", getWorldsFolder(), level.getWorldId(), level.getLevelId());
    }

    public static String getCurrentWorldCommonFolder() {
        return String.format("%s/%d/common", getWorldsFolder(), BomberManMatch.getInstance().getCurrentLevel().getWorldId());
    }

    public static String getPowerUpsFolder(){
        return getAssetsFolder() + "/powerups";
    }

    public static String getBackgroundImage(){
        return getAssetsFolder() + "/menu/background.jpg";
    }

    public static String getMainMenuWallpaper() {
        return Paths.getAssetsFolder() + "/menu/welcome.png";
    }
}
