package game.utils;

import game.BomberManMatch;
import game.Bomberman;
import game.level.Level;

public class Paths {
    public static String getPlayerDataPath() {
        return Paths.getDataFolder() + "/data";
    }

    public static String getDataFolder(){
        return "data";
    }

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
        Level level = Bomberman.getMatch().getCurrentLevel();
        return String.format("%s/%d/level/%d", getWorldsFolder(), level.getWorldId(), level.getLevelId());
    }

    public static String getCurrentWorldCommonFolder() {
        return String.format("%s/%d/common", getWorldsFolder(), Bomberman.getMatch().getCurrentLevel().getWorldId());
    }

    public static String getPowerUpsFolder(){
        return getAssetsFolder() + "/powerups";
    }

    public static String getPowerUpsBorderPath(){
        return getAssetsFolder() + "/menu/powerups_border.png";
    }

    public static String getBackgroundImage(){
        return getAssetsFolder() + "/menu/background.jpg";
    }

    public static String getMainMenuWallpaper() {
        return Paths.getAssetsFolder() + "/menu/welcome.png";
    }

    public static String getDeathWallpaper() {
        return Paths.getAssetsFolder() + "/menu/death.jpg";
    }

    public static String getWorldSelectorPortalPath(int id) {
        return String.format("%s/world_selector/world%d.png", Paths.getAssetsFolder(), id);
    }

    public static String getPowerupsLogoPath() {
        return getPowerUpsFolder() + "/powerups_logo.png";
    }

    public static String getSoundsPath() {
        return getAssetsFolder() + "/sounds";
    }

}
