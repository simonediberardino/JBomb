package game.utils;

import game.BomberManMatch;

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

    public static String getLevelsFolder(){
        return getAssetsFolder() + "/level";
    }

    public static String getCurrentLevelFolder(){
        return getLevelsFolder() + "/" + BomberManMatch.getInstance().getCurrentLevel().getId();
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
