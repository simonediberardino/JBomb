package game.utils

import game.Bomberman

object Paths {
    const val dataFolder: String = "data"
    private val assetsFolder: String = "assets"
    val playerDataPath: String = "$dataFolder/data"
    val entitiesFolder: String = "$assetsFolder/entities"
    val enemiesFolder: String = "$entitiesFolder/enemies"
    val worldsFolder: String = "$assetsFolder/worlds"
    val currentLevelFolder: String
        get() = Bomberman.getMatch().currentLevel.let { "$worldsFolder/${it.worldId}/level/${it.levelId}" }
    val currentWorldCommonFolder: String
        get() = String.format("%s/%d/common", worldsFolder, Bomberman.getMatch().currentLevel.worldId)
    val powerUpsFolder: String = "$assetsFolder/powerups"
    val menuImagesPath: String = "$assetsFolder/menu/images"
    val powerUpsBorderPath: String = "$menuImagesPath/powerups_border.png"
    val backgroundImage: String = "$menuImagesPath/background.jpg"
    val mainMenuWallpaper: String = "$menuImagesPath/welcome.png"
    val deathWallpaper: String = "$menuImagesPath/death.jpg"
    fun getWorldSelectorPortalPath(id: Int): String = "$assetsFolder/world_selector/world$id.png"
    val powerupsLogoPath: String = "$powerUpsFolder/powerups_logo.png"
    val inventoryPath: String = "$assetsFolder/inventory"
    val soundsPath: String = "$assetsFolder/sounds"
    val itemsPath: String = "$assetsFolder/items"
    val defaultSoundTrack: String = "$assetsFolder/menu/sound/soundtrack.wav"
    val uiFolder: String = "$assetsFolder/ui"
    val cursorPath: String = "$uiFolder/cursor.png"
    val iconPath: String = "$menuImagesPath/frame_icon.png"
    val xmlPath: String = "$assetsFolder/xml"
    val skinsXml: String = "$xmlPath/skins.xml"
}